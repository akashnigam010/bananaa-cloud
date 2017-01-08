package in.socyal.sc.app.checkin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.CancelCheckinResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.GetCheckinStatusResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.app.merchant.CheckinErrorCodeType;
import in.socyal.sc.app.merchant.MerchantQrMappingErrorCodeType;
import in.socyal.sc.helper.distance.DistanceHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.CheckinTaggedUserMappingDao;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.MerchantQrMappingDao;
import in.socyal.sc.persistence.UserDao;
import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;

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
	JwtTokenHelper jwtHelper;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CheckinResponseDto> getRestaurantCheckins(Integer restaurantId, Integer page) {
		List<CheckinResponseDto> checkins = new ArrayList<>();
		return checkins;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException {
		// Added by Akash : on confirm checkin, location check will be made
		// again
		MerchantQrMappingDto qrMapping = qrMappingDao.getMerchantQrMapping(request.getQrCode());
		if (qrMapping == null) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_NOT_FOUND);
		}

		Boolean isNearBy = DistanceHelper.isNearBy(request.getLocation().getLatitude(),
				request.getLocation().getLongitude(), qrMapping.getMerchant().getAddress().getLatitude(),
				qrMapping.getMerchant().getAddress().getLongitude());
		if (!isNearBy) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_CODE_LOCATION_OUT_OF_RANGE);
		}
		// TODO : Perform all these operations in a transaction
		ConfirmCheckinResponse response = new ConfirmCheckinResponse();
		// Fetching Merchant Details
		MerchantDto merchant = getMerchantDetailsFromQrMapping(request.getQrCode());
		// Preparing CheckIn Details
		CheckinDetailsDto dto = prepareCheckinDetails(request, merchant);
		// Performing A CheckIn
		Integer checkinId = checkinDao.confirmCheckin(dto);
		List<UserDto> taggedUserDetails = null;
		// Tagging the users to a CheckIn
		if (request.getTaggedUsers() != null && !request.getTaggedUsers().isEmpty()) {
			taggedUserDetails = getTaggedUserDetails(request.getTaggedUsers());
			taggedUserDao.tagUsersToACheckin(checkinId, request.getTaggedUsers());
		}

		// Building confirm checkin response
		response.setCheckinId(checkinId);
		response.setMerchantId(merchant.getId());
		response.setMerchantName(merchant.getName());
		response.setPreviousCheckinCount(dto.getPreviousCheckinCount());
		response.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
		if (taggedUserDetails != null) {
			response.setTaggedUsers(createTaggedUserResponse(taggedUserDetails));
		}

		return response;
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

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ValidateCheckinResponse validateCheckin(ValidateCheckinRequest request) throws BusinessException {
		ValidateCheckinResponse response = new ValidateCheckinResponse();
		MerchantQrMappingDto qrMapping = qrMappingDao.getMerchantQrMapping(request.getQrCode());
		if (qrMapping == null) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_NOT_FOUND);
		}

		Boolean isNearBy = DistanceHelper.isNearBy(request.getLocation().getLatitude(),
				request.getLocation().getLongitude(), qrMapping.getMerchant().getAddress().getLatitude(),
				qrMapping.getMerchant().getAddress().getLongitude());
		if (!isNearBy) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_CODE_LOCATION_OUT_OF_RANGE);
		}

		response.setPreviousCheckinCount(
				checkinDao.getPreviousCheckins(getCurrentUserId(), qrMapping.getMerchant().getId()));
		response.setMerchantName(qrMapping.getMerchant().getName());
		response.setShortAddress(qrMapping.getMerchant().getAddress().getLocality().getShortAddress());
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
			LOG.error("Checkin not found while getting checkin status :" + request.getId());
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}

		GetCheckinStatusResponse response = new GetCheckinStatusResponse();
		response.setCheckinId(checkin.getId());
		response.setPreviousCheckinCount(checkin.getPreviousCheckinCount());
		response.setMerchantId(checkin.getMerchant().getId());
		response.setMerchantName(checkin.getMerchant().getName());
		response.setShortAddress(checkin.getMerchant().getAddress().getLocality().getShortAddress());

		// FIXME : implements actual logic
		// adding temporary logic to return different statuses for testing
		// purpose
		if (checkin.getId() % 3 == 0) {
			response.setCheckinStatus(CheckinStatusType.APPROVED);
			response.setNewCheckinCount(checkin.getPreviousCheckinCount() + 1);
			response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getId()));
		} else if (checkin.getId() % 3 == 1) {
			response.setCheckinStatus(CheckinStatusType.CANCELLED);
		} else {
			response.setCheckinStatus(CheckinStatusType.PENDING);
			// FIXME : Need to keep the logic to fetch tagged users in cache
			response.setTaggedUsers(getTaggedUsersInCheckin(checkin.getId()));
		}

		return response;
	}

	private CheckinDetailsDto prepareCheckinDetails(ConfirmCheckinRequest request, MerchantDto merchant)
			throws BusinessException {
		CheckinDetailsDto checkinDetails = new CheckinDetailsDto();
		checkinDetails.setMerchantId(merchant.getId());
		Integer userId = getCurrentUserId();
		checkinDetails.setUserId(userId);
		checkinDetails.setCheckinDateTime(Calendar.getInstance());
		checkinDetails.setUpdatedDateTime(Calendar.getInstance());
		checkinDetails.setPreviousCheckinCount(getPreviousCheckins(userId, merchant.getId()));
		checkinDetails.setQrCode(request.getQrCode());
		checkinDetails.setStatus(CheckinStatusType.PENDING);
		return checkinDetails;
	}

	private MerchantDto getMerchantDetailsFromQrMapping(String qrCode) throws BusinessException {
		MerchantQrMappingDto dto = qrMappingDao.getMerchantQrMapping(qrCode);
		if (dto == null) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_NOT_FOUND);
		}

		return dto.getMerchant();
	}

	private Integer getPreviousCheckins(Integer userId, Integer merchantId) {
		return checkinDao.getPreviousCheckins(userId, merchantId);
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

	private List<TaggedUserResponse> getTaggedUsersInCheckin(Integer checkinId) {
		List<CheckinTaggedUserEntity> taggedUserEntities = taggedUserDao.getTaggedUsers(checkinId);
		List<Integer> taggedUserEntityIds = new ArrayList<>();
		for (CheckinTaggedUserEntity entity : taggedUserEntities) {
			taggedUserEntityIds.add(entity.getUserId());
		}
		return createTaggedUserResponse(getTaggedUserDetails(taggedUserEntityIds));
	}

	private Integer getCurrentUserId() {
		return Integer.valueOf(jwtHelper.getUserName());
	}
}
