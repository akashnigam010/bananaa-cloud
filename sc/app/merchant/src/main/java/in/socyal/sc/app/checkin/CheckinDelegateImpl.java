package in.socyal.sc.app.checkin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.CheckinStatusType;
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

@Service
public class CheckinDelegateImpl implements CheckinDelegate {
	@Autowired CheckinDao checkinDao;
	@Autowired MerchantQrMappingDao qrMappingDao;
	@Autowired MerchantDao merchantDao;
	@Autowired CheckinTaggedUserMappingDao taggedUserDao;
	@Autowired UserDao userDao;
	@Autowired JwtTokenHelper jwtHelper;
	
	@Override
	public List<CheckinResponseDto> getRestaurantCheckins(Integer restaurantId, Integer page) {
		List<CheckinResponseDto> checkins = new ArrayList<>();
		return checkins;
	}
	
	@Override
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException {
		ConfirmCheckinResponse response = new ConfirmCheckinResponse();
		//Fetching Merchant Details
		MerchantDto merchant = getMerchantDetailsFromQrMapping(request.getQrCode());
		//Preparing CheckIn Details
		CheckinDetailsDto dto = prepareCheckinDetails(request, merchant);
		//Performing A CheckIn
		Integer checkinId = checkinDao.confirmCheckin(dto);
		List<UserDto> taggedUserDetails = null;
		//Tagging the users to a CheckIn
		if (request.getTaggedUsers() != null && !request.getTaggedUsers().isEmpty()) {
			taggedUserDetails = getTaggedUserDetails(request.getTaggedUsers());
			taggedUserDao.tagUsersToACheckin(checkinId, request.getTaggedUsers());
		}
		response.setCheckinId(checkinId);
		response.setMerchantName(merchant.getName());
		response.setPreviousCheckinCount(dto.getPreviousCheckinCount());
		response.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
		if (taggedUserDetails != null) {
			List<TaggedUserResponse> list = new ArrayList<>();
			for (UserDto userDto : taggedUserDetails) {
				TaggedUserResponse taggedUserResponse = new TaggedUserResponse();
				taggedUserResponse.setId(userDto.getId());
				taggedUserResponse.setName(userDto.toString());
				list.add(taggedUserResponse);
			}
			
			response.setTaggedUsers(list);
		}
		
		return response;
	}
	
	private CheckinDetailsDto prepareCheckinDetails(ConfirmCheckinRequest request, MerchantDto merchant) throws BusinessException {
		CheckinDetailsDto checkinDetails = new CheckinDetailsDto();
		checkinDetails.setMerchantId(merchant.getId());
		//Fetch UserDetails from user token
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
			UserDto user = userDao.fetchUser(userId);
			if (user == null) {
				throw new BusinessException(CheckinErrorCodeType.USER_NOT_FOUND);
			}
			taggedUserDetails.add(user);
		}
		return taggedUserDetails;
	}

	@Override
	public ValidateCheckinResponse validateCheckin(ValidateCheckinRequest request) throws BusinessException {
		ValidateCheckinResponse response = new ValidateCheckinResponse();
		MerchantQrMappingDto qrMapping = qrMappingDao.getMerchantQrMapping(request.getQrCode());
		if (qrMapping == null) {
			throw new BusinessException(MerchantQrMappingErrorCodeType.QR_NOT_FOUND);
		}
		
		Boolean isNearBy = DistanceHelper.isNearBy(request.getLocation().getLatitude(), 
												  request.getLocation().getLongitude(), 
												  qrMapping.getMerchant().getAddress().getLatitude(), 
												  qrMapping.getMerchant().getAddress().getLongitude());
		if (!isNearBy) {
			throw new BusinessException(CheckinErrorCodeType.QR_CODE_OUT_OF_RANGE);
		}
		
		response.setPreviousCheckinCount(checkinDao.getPreviousCheckins(getCurrentUserId(), qrMapping.getMerchant().getId()));
		response.setMerchantName(qrMapping.getMerchant().getName());
		response.setShortAddress(qrMapping.getMerchant().getAddress().getLocality().getShortAddress());
		return response;
	}
	
	private Integer getCurrentUserId() {
		return Integer.valueOf(jwtHelper.getUserName());
	}
}
