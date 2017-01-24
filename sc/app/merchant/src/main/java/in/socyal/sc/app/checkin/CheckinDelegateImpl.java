package in.socyal.sc.app.checkin;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookOAuthException;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.checkin.request.AroundMeFeedsRequest;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
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
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.api.merchant.dto.Location;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.app.checkin.mapper.CheckinDelegateMapper;
import in.socyal.sc.app.checkin.type.CheckinErrorCodeType;
import in.socyal.sc.app.checkin.type.CheckinLikeErrorCodeType;
import in.socyal.sc.app.merchant.type.MerchantQrMappingErrorCodeType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.helper.distance.DistanceHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.facebook.OAuth2FbHelper;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.helper.type.GenericErrorCodeType;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.CheckinTaggedUserMappingDao;
import in.socyal.sc.persistence.CheckinUserLikeMappingDao;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.MerchantQrMappingDao;
import in.socyal.sc.persistence.UserDao;
import in.socyal.sc.persistence.mapper.UserDaoMapper;

@Service
public class CheckinDelegateImpl implements CheckinDelegate {
	private static final Logger LOG = Logger.getLogger(CheckinDelegateImpl.class);
	@Autowired
	CheckinDao checkinDao;
	@Autowired
	MerchantQrMappingDao qrMappingDao;
	@Autowired
	MerchantDao merchantDao;
	@Autowired
	CheckinTaggedUserMappingDao taggedUserDao;
	@Autowired
	UserDao userDao;
	@Autowired
	CheckinUserLikeMappingDao checkinLikeDao;
	@Autowired
	UserDaoMapper userMapper;
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	OAuth2FbHelper fbHelper;
	@Autowired
	Clock clock;
	@Autowired
	CheckinDelegateMapper checkinMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CheckinDto> getMerchantCheckins(Integer merchantId, Integer page) {
		List<CheckinDto> checkins = checkinDao.getMerchantCheckins(merchantId, page);
		return checkins;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FeedsResponse getMyFeeds(MyFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		List<CheckinDto> checkins = checkinDao.getUserCheckins(getCurrentUserId(), request.getPage());
		checkinMapper.map(checkins, response, request.getPage());
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FeedsResponse getProfileFeeds(ProfileFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		List<CheckinDto> checkins = checkinDao.getUserCheckins(request.getUserId(), request.getPage());
		checkinMapper.map(checkins, response, request.getPage());
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CheckinDto> getAroundMeFeeds(AroundMeFeedsRequest request) {
		//FIXME : Implement actual logic
		List<CheckinDto> checkins = checkinDao.getMerchantCheckins(12354, request.getPage());
		return checkins;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException {
		if (request.getShareOnFb()) {
			// FIXME : Temporarily commenting Share on FB logic until Bananaa App is registered with FB
			//checkForTokenValidity();
		}
		MerchantQrMappingDto qrMappingDetail = getQrDetails(request.getQrCode());
		checkForQrScanningRange(request.getLocation(), qrMappingDetail);
		CheckinDetailsDto dto = prepareCheckinDetails(request, qrMappingDetail.getMerchant());
		Integer checkinId = checkinDao.confirmCheckin(dto);
		List<UserDto> taggedUserDetails = tagUsers(request, checkinId);

		ConfirmCheckinResponse response = new ConfirmCheckinResponse();
		response.setCheckinId(checkinId);
		response.setMerchantId(qrMappingDetail.getMerchant().getId());
		response.setMerchantName(qrMappingDetail.getMerchant().getName());
		response.setPreviousCheckinCount(
				checkinDao.getUserCheckinsCountForAMerchant(getCurrentUserId(), qrMappingDetail.getMerchant().getId()));
		response.setShortAddress(qrMappingDetail.getMerchant().getAddress().getLocality().getShortAddress());
		if (taggedUserDetails != null) {
			response.setTaggedUsers(createTaggedUserResponse(taggedUserDetails));
		}

		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ValidateCheckinResponse validateCheckin(ValidateCheckinRequest request) throws BusinessException {
		ValidateCheckinResponse response = new ValidateCheckinResponse();
		MerchantQrMappingDto qrMappingDetails = getQrDetails(request.getQrCode());
		checkForQrScanningRange(request.getLocation(), qrMappingDetails);
		response.setPreviousCheckinCount(
				checkinDao.getUserCheckinsCountForAMerchant(getCurrentUserId(), qrMappingDetails.getMerchant().getId()));
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
		} else if (CheckinStatusType.CANCELLED == checkin.getStatus()) {
			LOG.error("Checkin is already cancelled for checkinID:" + request.getId());
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED);
		}
		checkinDao.cancelCheckin(request.getId());
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
		Integer checkinCount = checkinDao.getUserCheckinsCountForAMerchant(getCurrentUserId(),
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
		if (!validateIfLoggedInUser()) {
			throw new BusinessException(CheckinLikeErrorCodeType.USER_NOT_LOGGED_IN);
		}
		//validate whether checkin already exists
		CheckinDto checkin = checkinDao.getCheckin(request.getCheckinId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		// Write logic for validating whether a LIKE was already done
		if (checkinLikeDao.isCurrentCheckinLiked(request.getCheckinId(), getCurrentUserId())) {
			throw new BusinessException(CheckinLikeErrorCodeType.CHECKIN_ALREADY_LIKED);
		}
		checkinLikeDao.likeACheckin(request.getCheckinId(), getCurrentUserId());
		response.setLikeCount(fetchLikeCount(request.getCheckinId()));
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LikeCheckinResponse unLikeACheckin(LikeCheckinRequest request) throws BusinessException {
		LikeCheckinResponse response = new LikeCheckinResponse();
		//validate whether current user is logged in or not
		if (!validateIfLoggedInUser()) {
			throw new BusinessException(CheckinLikeErrorCodeType.USER_NOT_LOGGED_IN);
		}
		//validate whether checkin already exists
		CheckinDto checkin = checkinDao.getCheckin(request.getCheckinId());
		if (checkin == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		// Write logic for validating whether a LIKE was done or not
		if (!checkinLikeDao.isCurrentCheckinLiked(request.getCheckinId(), getCurrentUserId())) {
			throw new BusinessException(CheckinLikeErrorCodeType.CHECKIN_NOT_LIKED);
		}
		checkinLikeDao.unLikeACheckin(request.getCheckinId(), getCurrentUserId());
		response.setLikeCount(fetchLikeCount(request.getCheckinId()));
		return response;
	}
	
	public Integer fetchLikeCount(Integer checkinId) {
		return checkinLikeDao.fetchLikeCount(checkinId);
	}

	private void checkForTokenValidity() throws BusinessException {
		boolean isTokenValid = false;
		UserDto user = userDao.fetchUser(getCurrentUserId());
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

	private void checkForQrScanningRange(Location location, MerchantQrMappingDto qrMappingDetail) {
		Boolean isNearBy = DistanceHelper.isNearBy(location.getLatitude(), location.getLongitude(),
				qrMappingDetail.getMerchant().getAddress().getLatitude(),
				qrMappingDetail.getMerchant().getAddress().getLongitude());
		if (!isNearBy) {
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
		Integer userId = getCurrentUserId();
		checkinDetails.setUserId(userId);
		checkinDetails.setCheckinDateTime(clock.cal());
		checkinDetails.setUpdatedDateTime(clock.cal());
		checkinDetails.setQrCode(request.getQrCode());
		checkinDetails.setStatus(CheckinStatusType.PENDING);
		return checkinDetails;
	}

	private List<UserDto> getTaggedUserDetails(List<Integer> taggedUserIds) throws BusinessException {
		List<UserDto> taggedUserDetails = new ArrayList<>();
		for (Integer userId : taggedUserIds) {
			// FIXME : Instead of single user per DB call, pass list of user ids
			// and get back list of users
			UserDto user = userDao.fetchUser(userId);
			if (user == null) {
				throw new BusinessException(CheckinErrorCodeType.USER_NOT_FOUND);
			}
			taggedUserDetails.add(user);
		}
		return taggedUserDetails;
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

	private Integer getCurrentUserId() {
		return Integer.valueOf(jwtHelper.getUserName());
	}
	
	/**
	 * check if user is logged in or not
	 * FIXME : Move such logics to a common place
	 */
	private boolean validateIfLoggedInUser() {
		List<String> roles = jwtHelper.getRoles();
		for (String role : roles) {
			if (RoleType.USER == RoleType.getRole(role)) {
				return true;
			}
		}
		return false;
	}
}
