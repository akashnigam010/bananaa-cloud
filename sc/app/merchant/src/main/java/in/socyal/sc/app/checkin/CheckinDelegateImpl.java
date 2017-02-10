package in.socyal.sc.app.checkin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookOAuthException;

import in.socyal.sc.api.checkin.business.request.BusinessApproveCheckinRequest;
import in.socyal.sc.api.checkin.business.request.BusinessCancelCheckinRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinDetailsRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinHistoryRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinsRequest;
import in.socyal.sc.api.checkin.business.response.BusinessApproveCheckinResponse;
import in.socyal.sc.api.checkin.business.response.BusinessCancelCheckinResponse;
import in.socyal.sc.api.checkin.business.response.BusinessCheckin;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinDetailsResponse;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinHistoryResponse;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinsResponse;
import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.checkin.request.AroundMeFeedsRequest;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.GetMerchantCheckinsRequest;
import in.socyal.sc.api.checkin.request.LikeCheckinRequest;
import in.socyal.sc.api.checkin.request.MyFeedsRequest;
import in.socyal.sc.api.checkin.request.ProfileFeedsRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.CancelCheckinResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.FeedsResponse;
import in.socyal.sc.api.checkin.response.GetCheckinStatusResponse;
import in.socyal.sc.api.checkin.response.LikeCheckinResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.api.feedback.response.FeedbackDetailsResponse;
import in.socyal.sc.api.merchant.dto.Location;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.app.checkin.mapper.CheckinDelegateMapper;
import in.socyal.sc.app.checkin.type.CheckinErrorCodeType;
import in.socyal.sc.app.checkin.type.CheckinLikeErrorCodeType;
import in.socyal.sc.app.merchant.type.MerchantQrMappingErrorCodeType;
import in.socyal.sc.date.type.DateFormatType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.date.util.DayUtil;
import in.socyal.sc.helper.JwtTokenDetailsHelper;
import in.socyal.sc.helper.distance.DistanceHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.facebook.OAuth2FbHelper;
import in.socyal.sc.helper.type.GenericErrorCodeType;
import in.socyal.sc.notification.NotificationDelegate;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.CheckinTaggedUserMappingDao;
import in.socyal.sc.persistence.CheckinUserLikeMappingDao;
import in.socyal.sc.persistence.FeedbackDao;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.MerchantQrMappingDao;
import in.socyal.sc.persistence.UserDao;
import in.socyal.sc.persistence.UserFollowerMappingDao;
import in.socyal.sc.persistence.mapper.UserDaoMapper;

@Service
public class CheckinDelegateImpl implements CheckinDelegate {
	private static final Logger LOG = Logger.getLogger(CheckinDelegateImpl.class);
	private static final String CHECKIN_CANCELLED_BY_USER = "Checkin cancelled by USER";
	private static final String CHECKIN_CANCELLED_BY_MERCHANT = "Checkin cancelled by MERCHANT";
	
	@Autowired CheckinDao checkinDao;
	@Autowired MerchantQrMappingDao qrMappingDao;
	@Autowired MerchantDao merchantDao;
	@Autowired CheckinTaggedUserMappingDao taggedUserDao;
	@Autowired UserDao userDao;
	@Autowired CheckinUserLikeMappingDao checkinLikeDao;
	@Autowired UserDaoMapper userMapper;
	@Autowired OAuth2FbHelper fbHelper;
	@Autowired Clock clock;
	@Autowired CheckinDelegateMapper checkinMapper;
	@Autowired DayUtil dayUtil;
	@Autowired UserFollowerMappingDao userFollowerDao;
	@Autowired FeedbackDao feedbackDao;
	@Autowired NotificationDelegate notificationDelegate;
	@Autowired JwtTokenDetailsHelper jwtDetailsHelper;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FeedsResponse getMerchantCheckins(GetMerchantCheckinsRequest request) {
		FeedsResponse response = new FeedsResponse();
		List<CheckinDto> checkins = checkinDao.getMerchantCheckins(request.getId(), request.getPage());
		Map<Integer, Integer> userApprovedCheckins = new HashMap<>();
		//TODO : rather than hitting DB frequently fetch checkin count at once
		for (CheckinDto checkin : checkins) {
			Integer userId = checkin.getUser().getId();
			userApprovedCheckins.put(userId, checkinDao.getUserCheckinCount(userId));
		}
		checkinMapper.map(checkins, response, userApprovedCheckins);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FeedsResponse getMyFeeds(MyFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		//Fetching user id list whom the current user is following including current user
		List<Integer> userIds = userFollowerDao.fetchMyFriendsIds(jwtDetailsHelper.getCurrentUserId());
		List<CheckinDto> checkins = checkinDao.getUserCheckins(userIds, request.getPage());
		Map<Integer, Integer> userApprovedCheckins = new HashMap<>();
		//TODO : rather than hitting DB frequently fetch checkin count at once
		for (Integer userId : userIds) {
			userApprovedCheckins.put(userId, checkinDao.getUserCheckinCount(userId));
		}
		checkinMapper.map(checkins, response, userApprovedCheckins);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FeedsResponse getProfileFeeds(ProfileFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		List<CheckinDto> checkins = checkinDao.getUserCheckins(Collections.singletonList(request.getUserId()), request.getPage());
		Map<Integer, Integer> userApprovedCheckins = new HashMap<>();
		userApprovedCheckins.put(request.getUserId(), checkinDao.getUserCheckinCount(request.getUserId()));
		checkinMapper.map(checkins, response, userApprovedCheckins);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FeedsResponse getAroundMeFeeds(AroundMeFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		//TODO: Fix this condition for fetching data only in a particular city based on location
		List<CheckinDto> checkins = null;
		if (jwtDetailsHelper.isUserLoggedIn()) {
		checkins = checkinDao.getAroundMeFeedsDao(jwtDetailsHelper.getCurrentUserId(), request.getPage());
		} else {
			checkins = checkinDao.getAroundMeFeedsDao(null, request.getPage());
		}
		Map<Integer, Integer> userApprovedCheckins = new HashMap<>();
		//TODO : rather than hitting DB frequently fetch checkin count at once
		for (CheckinDto checkin : checkins) {
			Integer userId = checkin.getUser().getId();
			userApprovedCheckins.put(userId, checkinDao.getUserCheckinCount(userId));
		}
		checkinMapper.map(checkins, response, userApprovedCheckins);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException {
		//TODO : Temporarily commenting Share on FB logic until Bananaa App is registered with FB
		/*if (request.getShareOnFb()) {
			checkForTokenValidity();
		}*/
		MerchantQrMappingDto qrMappingDetails = getQrDetails(request.getQrCode());
		validateQrCodeStatusAndCheckinLocation(request.getLocation(), qrMappingDetails);
		CheckinDetailsDto dto = prepareCheckinDetails(request, qrMappingDetails.getMerchant());
		Integer checkinId = checkinDao.confirmCheckin(dto);
		List<UserDto> taggedUserDetails = tagUsers(request, checkinId);
		ConfirmCheckinResponse response = new ConfirmCheckinResponse();
		response.setCheckinId(checkinId);
		response.setMerchantId(qrMappingDetails.getMerchant().getId());
		response.setMerchantName(qrMappingDetails.getMerchant().getName());
		response.setPreviousCheckinCount(checkinDao.getUserCheckinsCountForAMerchant(
				jwtDetailsHelper.getCurrentUserId(), qrMappingDetails.getMerchant().getId()));
		response.setShortAddress(qrMappingDetails.getMerchant().getAddress().getLocality().getShortAddress());
		if (taggedUserDetails != null) {
			response.setTaggedUsers(createTaggedUserResponse(taggedUserDetails));
		}

		//FIXME
		//send notification to the MERCHANT
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ValidateCheckinResponse validateCheckin(ValidateCheckinRequest request) throws BusinessException {
		ValidateCheckinResponse response = new ValidateCheckinResponse();
		MerchantQrMappingDto qrMappingDetails = getQrDetails(request.getQrCode());
		validateQrCodeStatusAndCheckinLocation(request.getLocation(), qrMappingDetails);
		response.setPreviousCheckinCount(checkinDao.getUserCheckinsCountForAMerchant(
				jwtDetailsHelper.getCurrentUserId(), qrMappingDetails.getMerchant().getId()));
		response.setMerchantName(qrMappingDetails.getMerchant().getName());
		response.setShortAddress(qrMappingDetails.getMerchant().getAddress().getLocality().getShortAddress());
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public CancelCheckinResponse cancelCheckin(CancelCheckinRequest request) throws BusinessException {
		CancelCheckinResponse response = new CancelCheckinResponse();
		CheckinDto checkin = checkinDao.getCheckin(request.getId());
		if (checkin == null) {
			LOG.error("Cancel checkin failed because Checkin ID was not found :" + request.getId());
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		} else if (CheckinStatusType.USER_CANCELLED == checkin.getStatus()) {
			LOG.error("Checkin is already cancelled for checkinID:" + request.getId());
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED);
		}
		checkinDao.cancelCheckin(request.getId());
		//FIXME
		//send notification to MERCHANT
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GetCheckinStatusResponse getCheckinStatus(CheckinRequest request) throws BusinessException {
		CheckinDto checkin = checkinDao.getCheckin(request.getId());
		if (checkin == null) {
			LOG.error("Checkin not found while trying to fetch checkin status :" + request.getId());
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}

		// Fetch previous checkin count
		Integer checkinCount = checkinDao.getUserCheckinsCountForAMerchant(jwtDetailsHelper.getCurrentUserId(),
				checkin.getMerchant().getId());
		GetCheckinStatusResponse response = new GetCheckinStatusResponse();
		response.setCheckinId(checkin.getId());
		response.setMerchantId(checkin.getMerchant().getId());
		response.setMerchantName(checkin.getMerchant().getName());
		response.setShortAddress(checkin.getMerchant().getAddress().getLocality().getShortAddress());
		response.setCheckinStatus(checkin.getStatus());
		if (checkin.getStatus() == CheckinStatusType.PENDING) {
			response.setPreviousCheckinCount(checkinCount);
			response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getTaggedUsers()));
		} else if (checkin.getStatus() == CheckinStatusType.APPROVED) {
			response.setNewCheckinCount(checkinCount);
			response.setPreviousCheckinCount(checkinCount - 1);
			response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getTaggedUsers()));
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LikeCheckinResponse likeACheckin(LikeCheckinRequest request) throws BusinessException {
		LikeCheckinResponse response = new LikeCheckinResponse();
		//validate whether current user is logged in or not
		if (!jwtDetailsHelper.isUserLoggedIn()) {
			throw new BusinessException(CheckinLikeErrorCodeType.USER_NOT_LOGGED_IN);
		}
		//validate whether checkin already exists
		CheckinDto checkin = checkinDao.getCheckin(request.getCheckinId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		// Write logic for validating whether a LIKE was already done
		if (checkinLikeDao.isCurrentCheckinLiked(request.getCheckinId(), jwtDetailsHelper.getCurrentUserId())) {
			throw new BusinessException(CheckinLikeErrorCodeType.CHECKIN_ALREADY_LIKED);
		}
		checkinLikeDao.likeACheckin(request.getCheckinId(), jwtDetailsHelper.getCurrentUserId());
		response.setLikeCount(fetchLikeCount(request.getCheckinId()));
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LikeCheckinResponse unLikeACheckin(LikeCheckinRequest request) throws BusinessException {
		LikeCheckinResponse response = new LikeCheckinResponse();
		//validate whether current user is logged in or not
		if (!jwtDetailsHelper.isUserLoggedIn()) {
			throw new BusinessException(CheckinLikeErrorCodeType.USER_NOT_LOGGED_IN);
		}
		//validate whether checkin already exists
		CheckinDto checkin = checkinDao.getCheckin(request.getCheckinId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		// Write logic for validating whether a LIKE was done or not
		if (!checkinLikeDao.isCurrentCheckinLiked(request.getCheckinId(), jwtDetailsHelper.getCurrentUserId())) {
			throw new BusinessException(CheckinLikeErrorCodeType.CHECKIN_NOT_LIKED);
		}
		checkinLikeDao.unLikeACheckin(request.getCheckinId(), jwtDetailsHelper.getCurrentUserId());
		response.setLikeCount(fetchLikeCount(request.getCheckinId()));
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GetBusinessCheckinsResponse getBusinessCheckins(GetBusinessCheckinsRequest request) {
		GetBusinessCheckinsResponse response = new GetBusinessCheckinsResponse();
		Calendar checkinDate = getDateFromIdentifier(request.getDateIdentifier());
		Calendar checkinNextDate = getImmediateNextDateFromIdentifier(request.getDateIdentifier());
		List<CheckinDto> checkins = checkinDao.getBusinessCheckins(request.getPage(), checkinDate, checkinNextDate,
				jwtDetailsHelper.getCurrentMerchantId());
		response.setDate(dayUtil.formatDate(checkinDate, DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		response.setCheckinCount(checkinDao.getBusinessCheckinsCountPerDay(request.getPage(), checkinDate, checkinNextDate,
				jwtDetailsHelper.getCurrentMerchantId()));
		Map<Integer, Integer> userApprovedCheckins = new HashMap<>();
		for (CheckinDto checkin : checkins) {
			Integer userId = checkin.getUser().getId();
			userApprovedCheckins.put(userId, checkinDao.getUserCheckinsCountForAMerchant(userId, checkin.getMerchant().getId()));
		}
		checkinMapper.map(checkins, response, userApprovedCheckins);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GetBusinessCheckinDetailsResponse getBusinessCheckinDetails(GetBusinessCheckinDetailsRequest request)
			throws BusinessException {
		CheckinDto checkin = checkinDao.getCheckin(request.getCheckinId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		Integer userCheckinCount = checkinDao.getUserCheckinsCountForAMerchant(checkin.getUser().getId(),
				checkin.getMerchant().getId());
		GetBusinessCheckinDetailsResponse response = buildGetBusinessCheckinDetailsResponse(checkin, userCheckinCount);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GetBusinessCheckinHistoryResponse getBusinessCheckinHistory(GetBusinessCheckinHistoryRequest request)
			throws BusinessException {
		GetBusinessCheckinHistoryResponse response = getGetBusinessCheckinHistoryResponse(request);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessCancelCheckinResponse businessCancelCheckin(BusinessCancelCheckinRequest request)
			throws BusinessException {
		CheckinDto checkin = checkinDao.businessCancelCheckin(request.getCheckinId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		Integer userCheckinCount = checkinDao.getUserCheckinsCountForAMerchant(checkin.getUser().getId(),
				checkin.getMerchant().getId());
		//FIXME
		//send notification to the user
		BusinessCancelCheckinResponse response = businessCancelCheckinResponse(checkin, userCheckinCount);
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessApproveCheckinResponse businessApproveCheckin(BusinessApproveCheckinRequest request)
			throws BusinessException {
		//1. Updating CheckinStatus to APPROVED
		CheckinDto checkin = checkinDao.businessApproveCheckin(request.getCheckinId(), jwtDetailsHelper.getCurrentMerchantId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		Integer userCheckinCount = checkinDao.getUserCheckinsCountForAMerchant(checkin.getUser().getId(),
				checkin.getMerchant().getId());
		//2. Increase merchant checkin count in merchant table
		merchantDao.updateMerchantCheckinCountDetails(checkin.getMerchant().getId());
		//FIXME
		//3. send notification to the user
		return businessApproveCheckinResponse(checkin, checkin.getFeedback(), userCheckinCount);
	}
	
	public Integer fetchLikeCount(Integer checkinId) {
		return checkinLikeDao.fetchLikeCount(checkinId);
	}
	
	private Calendar getDateFromIdentifier(Integer identifier) {
		Calendar date = clock.cal();
		date.add(Calendar.DAY_OF_MONTH, (-1 * (identifier)));
		return date;
	}
	
	private Calendar getImmediateNextDateFromIdentifier(Integer identifier) {
		Calendar date = clock.cal();
		date.add(Calendar.DAY_OF_MONTH, ((-1 * (identifier)) + 1));
		return date;
	}

	@SuppressWarnings("unused")
	private void checkForTokenValidity() throws BusinessException {
		boolean isTokenValid = false;
		UserDto user = userDao.fetchUser(jwtDetailsHelper.getCurrentUserId());
		if (user == null) {
			throw new BusinessException(CheckinErrorCodeType.USER_NOT_FOUND);
		}
		try {
			isTokenValid = fbHelper.checkForTokenValidity(user.getFacebookToken());
		} catch (FacebookOAuthException e) {
			LOG.error("Error while inspecting user token from FB, UserId : " + user.getId() + ", Error Message : "
					+ e.getErrorMessage());
		}
		if (!isTokenValid) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
	}

	private MerchantQrMappingDto getQrDetails(String qrCode) throws BusinessException {
		MerchantQrMappingDto qrMappingDetails = qrMappingDao.getMerchantQrMapping(qrCode);
		if (qrMappingDetails == null) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_NOT_FOUND);
		}
		return qrMappingDetails;
	}

	private void validateQrCodeStatusAndCheckinLocation(Location location, MerchantQrMappingDto qrMappingDetails) {
		if (!qrMappingDetails.getStatus()) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_CODE_DISABLED);
		}
		Boolean isQrCodeInRange = DistanceHelper.isCoordinateInRange(location.getLatitude(), location.getLongitude(),
				qrMappingDetails.getMerchant().getAddress().getLatitude(),
				qrMappingDetails.getMerchant().getAddress().getLongitude());
		if (!isQrCodeInRange) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_CODE_LOCATION_OUT_OF_RANGE);
		}
	}

	private List<UserDto> tagUsers(ConfirmCheckinRequest request, Integer checkinId) {
		List<UserDto> taggedUserDetails = null;
		if (request.getTaggedUsers() != null && !request.getTaggedUsers().isEmpty()) {
			taggedUserDetails = getTaggedUserDetails(request.getTaggedUsers());
			taggedUserDao.tagUsersToACheckin(checkinId, request.getTaggedUsers());
		}
		return taggedUserDetails;
	}

	private CheckinDetailsDto prepareCheckinDetails(ConfirmCheckinRequest request, MerchantDto merchant)
			throws BusinessException {
		CheckinDetailsDto checkinDetails = new CheckinDetailsDto();
		checkinDetails.setMerchantId(merchant.getId());
		checkinDetails.setUserId(jwtDetailsHelper.getCurrentUserId());
		checkinDetails.setCheckinDateTime(clock.cal());
		checkinDetails.setUpdatedDateTime(clock.cal());
		checkinDetails.setQrCode(request.getQrCode());
		checkinDetails.setStatus(CheckinStatusType.PENDING);
		checkinDetails.setRewardStatus(RewardStatusType.NOT_GIVEN);
		return checkinDetails;
	}

	private List<UserDto> getTaggedUserDetails(List<Integer> taggedUserIds) throws BusinessException {
		return userDao.fetchUsersByIds(taggedUserIds);
	}

	private List<TaggedUserResponse> getTaggedUsersInCheckin(List<CheckinTaggedUserDto> taggedUsers) {
		List<UserDto> users = new ArrayList<>();
		for (CheckinTaggedUserDto taggedUser : taggedUsers) {
			users.add(taggedUser.getUser());
		}
		return createTaggedUserResponse(users);
	}

	private List<TaggedUserResponse> createTaggedUserResponse(List<UserDto> taggedUserDetails) {
		List<TaggedUserResponse> list = new ArrayList<>();
		for (UserDto userDto : taggedUserDetails) {
			TaggedUserResponse taggedUserResponse = new TaggedUserResponse();
			taggedUserResponse.setId(userDto.getId());
			taggedUserResponse.setName(userDto.getName());
			list.add(taggedUserResponse);
		}
		return list;
	}

	private GetBusinessCheckinDetailsResponse buildGetBusinessCheckinDetailsResponse(CheckinDto checkin,
			Integer userCheckinCount) {
		GetBusinessCheckinDetailsResponse response = new GetBusinessCheckinDetailsResponse();
		UserDto user = checkin.getUser();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		userDetails.setId(user.getId());
		userDetails.setImageUrl(user.getImageUrl());
		userDetails.setName(user.getName());
		userDetails.setUserCheckins(userCheckinCount);
		response.setUser(userDetails);
		response.setCardNumber(checkin.getMerchantQrMapping().getCardId());
		response.setCheckinStatus(checkin.getStatus());
		if (CheckinStatusType.USER_CANCELLED == checkin.getStatus()) {
			response.setCancelMessage(CHECKIN_CANCELLED_BY_USER);
		} else if (CheckinStatusType.MERCHANT_CANCELLED == checkin.getStatus()) {
			response.setCancelMessage(CHECKIN_CANCELLED_BY_MERCHANT);
		}
		response.setRewardStatus(checkin.getRewardStatus());
		response.setRewardMessage(checkin.getRewardMessage());
		FeedbackDto feedback = checkin.getFeedback();
		response.setFeedbackStatus(feedback.getStatus());
		if (FeedbackStatusType.RECEIVED == feedback.getStatus()) {
			FeedbackDetailsResponse feedbackDetails = new FeedbackDetailsResponse();
			feedbackDetails.setFoodRating(feedback.getFoodRating());
			feedbackDetails.setAmbienceRating(feedback.getAmbienceRating());
			feedbackDetails.setServiceRating(feedback.getServiceRating());
			response.setFeedbackDetails(feedbackDetails);
		}
		return response;
	}

	//FIXME : dummy response, replace with actual logic
	private GetBusinessCheckinHistoryResponse getGetBusinessCheckinHistoryResponse(GetBusinessCheckinHistoryRequest request) {
		GetBusinessCheckinHistoryResponse response = new GetBusinessCheckinHistoryResponse();
		List<BusinessCheckin> list = new ArrayList<>();
		if (request.getPage() == 1) {
			BusinessCheckin checkin = new BusinessCheckin();
			checkin.setId(1);
			checkin.setCard("15");
			checkin.setCheckinStatus(CheckinStatusType.APPROVED);
			FeedbackDetailsResponse feedbackDetails = new FeedbackDetailsResponse();
			feedbackDetails.setFoodRating(4);
			feedbackDetails.setAmbienceRating(3);
			feedbackDetails.setServiceRating(1);
			checkin.setFeedbackDetails(feedbackDetails);
			checkin.setRating(4.5);
			checkin.setRewardMessage("Won Amazon gift coupon worth Rs. 100!");
			List<TaggedUserResponse> taggedUsers = new ArrayList<>();
			TaggedUserResponse taggedUser = new TaggedUserResponse();
			taggedUser.setId(1);
			taggedUser.setName("Dharmasena");
			taggedUsers.add(taggedUser);
			checkin.setTaggedUsers(taggedUsers);
			checkin.setTimestamp(clock.cal().getTime());
			UserDetailsResponse userDetails = new UserDetailsResponse();
			userDetails.setId(request.getUserId());
			userDetails.setImageUrl("https://scontent.xx.fbcdn.net/v/t1.0-1/c1.0.160.160/p160x160/15578891_1180564885332226_632797692936181444_n.jpg?oh=7834859a26b7b40c9801ad1e563e9015&oe=58FF1D94");
			userDetails.setName("Akash Nigam");
			userDetails.setUserCheckins(20);
			checkin.setUser(userDetails);
			list.add(checkin);
			list.add(checkin);
			list.add(checkin);
			list.add(checkin);
			list.add(checkin);
		} else {
			list = Collections.emptyList();
		}
		response.setCheckins(list);
		return response;
	}
	
	private BusinessCancelCheckinResponse businessCancelCheckinResponse(CheckinDto checkin, Integer userCheckinCount) {
		BusinessCancelCheckinResponse response = new BusinessCancelCheckinResponse();
		UserDto user = checkin.getUser();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		userDetails.setId(user.getId());
		userDetails.setImageUrl(user.getImageUrl());
		userDetails.setName(user.getName());
		userDetails.setUserCheckins(userCheckinCount);
		response.setUser(userDetails);
		response.setCardNumber(checkin.getMerchantQrMapping().getCardId());
		response.setCheckinStatus(checkin.getStatus());
		response.setCancelMessage(CHECKIN_CANCELLED_BY_MERCHANT);
		return response;
	}
	
	private BusinessApproveCheckinResponse businessApproveCheckinResponse(CheckinDto checkin, FeedbackDto feedbackDto,
			Integer userCheckinCount) {
		BusinessApproveCheckinResponse response = new BusinessApproveCheckinResponse();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		UserDto user = checkin.getUser();
		userDetails.setId(user.getId());
		userDetails.setImageUrl(user.getImageUrl());
		userDetails.setName(user.getName());
		userDetails.setUserCheckins(userCheckinCount);
		response.setUser(userDetails);
		response.setCardNumber(checkin.getMerchantQrMapping().getCardId());
		response.setCheckinStatus(checkin.getStatus());
		response.setRewardStatus(checkin.getRewardStatus());
		response.setFeedbackStatus(feedbackDto.getStatus());
		return response;
	}
}
