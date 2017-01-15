package in.socyal.sc.app.checkin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookOAuthException;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.LikeCheckinRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.CancelCheckinResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.GetCheckinStatusResponse;
import in.socyal.sc.api.checkin.response.LikeCheckinResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.api.merchant.dto.Location;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.app.merchant.CheckinErrorCodeType;
import in.socyal.sc.app.merchant.CheckinLikeErrorCodeType;
import in.socyal.sc.app.merchant.MerchantQrMappingErrorCodeType;
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
import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;
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

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CheckinDto> getMerchantCheckins(Integer merchantId, Integer page) {
		List<CheckinDto> checkins = checkinDao.getMerchantCheckins(merchantId, page);
		return checkins;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException {
		if (request.getShareOnFb()) {
			checkForTokenValidity();
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
		// original logic - uncomment after business app integration
		// response.setCheckinStatus(checkin.getStatus());
		// if (checkin.getStatus() == CheckinStatusType.PENDING) {
		// response.setPreviousCheckinCount(checkinCount);
		// response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getTaggedUsers()));
		// } else if (checkin.getStatus() == CheckinStatusType.APPROVED) {
		// response.setPreviousCheckinCount(checkinCount - 1);
		// response.setNewCheckinCount(checkinCount);
		// response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getTaggedUsers()));

		// FIXME : implements actual logic
		// adding temporary logic to return different statuses for testing
		// purpose
		if (checkin.getId() % 3 == 0) {
			response.setCheckinStatus(CheckinStatusType.APPROVED);
			response.setNewCheckinCount(1);
			response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getId()));
		} else if (checkin.getId() % 3 == 1) {
			response.setCheckinStatus(CheckinStatusType.CANCELLED);
			response.setCancelMessage("Your checkin has been cancelled by Merchant");
		} else {
			response.setCheckinStatus(CheckinStatusType.PENDING);
			// FIXME : Need to keep the logic to fetch tagged users in cache
			response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getId()));
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LikeCheckinResponse likeACheckin(LikeCheckinRequest request) throws BusinessException {
		LikeCheckinResponse response = new LikeCheckinResponse();
		// Write logic for validating whether a LIKE was already done
		if (checkinLikeDao.isCurrentCheckinLiked(request.getCheckinId(), getCurrentUserId())) {
			throw new BusinessException(CheckinLikeErrorCodeType.CHECKIN_ALREADY_LIKED);
		}
		checkinLikeDao.likeACheckin(request.getCheckinId(), getCurrentUserId());
		return response;
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
			LOG.error("Error while inspecting user token from FB, UserId : " + user.getId() + "Error Message : "
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
		checkinDetails.setCheckinDateTime(Calendar.getInstance());
		checkinDetails.setUpdatedDateTime(Calendar.getInstance());
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

	// private List<TaggedUserResponse>
	// getTaggedUsersInCheckin(List<CheckinTaggedUserDto> taggedUsers) {
	// List<UserDto> users = new ArrayList<>();
	// for (CheckinTaggedUserDto taggedUser : taggedUsers) {
	// users.add(taggedUser.getUser());
	// }
	// return createTaggedUserResponse(users);
	// }

	private List<TaggedUserResponse> getTaggedUsersInCheckin(Integer checkinId) throws BusinessException {
		List<CheckinTaggedUserEntity> taggedUserEntities = taggedUserDao.getTaggedUsers(checkinId);
		List<Integer> taggedUserEntityIds = new ArrayList<>();
		for (CheckinTaggedUserEntity entity : taggedUserEntities) {
			taggedUserEntityIds.add(entity.getUser().getId());
		}
		return createTaggedUserResponse(getTaggedUserDetails(taggedUserEntityIds));
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
}
