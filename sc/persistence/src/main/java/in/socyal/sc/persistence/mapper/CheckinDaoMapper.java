package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinFilterCriteria;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;
import in.socyal.sc.persistence.entity.CheckinUserLikeEntity;
import in.socyal.sc.persistence.entity.FeedbackEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.MerchantQrMappingEntity;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class CheckinDaoMapper {
	@Autowired MerchantDaoMapper merchantMapper;
	@Autowired CheckinTaggedUserMapper taggedUserMapper;
	@Autowired CheckinUserLikeMapper likeMapper;
	@Autowired UserDaoMapper userDaoMapper;
	@Autowired MerchantQrMappingMapper qrCodeMapper;
	@Autowired FeedbackDaoMapper feedbackDaoMapper;
	@Autowired JwtTokenHelper jwtHelper;
	
	public void mapToCheckinEntity(CheckinDetailsDto from, CheckinEntity to) {
		MerchantEntity merchant = new MerchantEntity();
		merchant.setId(from.getMerchantId());
		MerchantQrMappingEntity qrMapping = new MerchantQrMappingEntity();
		qrMapping.setQrCode(from.getQrCode());
		UserEntity user = new UserEntity();
		user.setId(from.getUserId());
		FeedbackEntity feedback = new FeedbackEntity();
		feedback.setUserId(from.getUserId());
		feedback.setMerchantId(from.getMerchantId());
		feedback.setStatus(from.getFeedbackStatus());
		to.setMerchant(merchant);
		to.setMerchantQrMapping(qrMapping);
		to.setUser(user);
		to.setStatus(from.getStatus());
		to.setCheckinDateTime(from.getCheckinDateTime());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setRewardStatus(from.getRewardStatus());
		to.setFeedback(feedback);
	}
	
	public void mapToCheckinDto(CheckinEntity from, CheckinDto to, CheckinFilterCriteria filter) {
		to.setId(from.getId());
		to.setMerchantId(from.getMerchant().getId());
		to.setStatus(from.getStatus());
		to.setCheckinDateTime(from.getCheckinDateTime());		
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setRewardStatus(from.getRewardStatus());
		to.setRewardMessage(from.getRewardMessage());
		to.setFeedback(feedbackDaoMapper.mapToFeedbackDto(from.getFeedback()));
		to.setLiked(hasLiked(from));
		to.setLikeCount(from.getLikes().size());
		
		if (filter.getMapQrDetails()) {
			MerchantQrMappingDto qrMappingDto = new MerchantQrMappingDto();
			MerchantFilterCriteria merchantFilter = new MerchantFilterCriteria(false, true);
			qrCodeMapper.map(from.getMerchantQrMapping(), qrMappingDto, merchantFilter);
			to.setMerchantQrMapping(qrMappingDto);
		}
		
		if (filter.getMapUser()) {
			UserDto user = new UserDto();
			userDaoMapper.map(from.getUser(), user);
			to.setUser(user);
		}
		
		if (filter.getMapTaggedUsers()) {
			List<CheckinTaggedUserDto> taggedUsers = new ArrayList<>();
			for (CheckinTaggedUserEntity taggedUser : from.getTaggedUsers()) {
				CheckinTaggedUserDto taggedUserDto = new CheckinTaggedUserDto();
				taggedUserMapper.map(taggedUser, taggedUserDto);
				taggedUsers.add(taggedUserDto);
			}
			to.setTaggedUsers(taggedUsers);
		}
	}
	
	private boolean hasLiked(CheckinEntity from) {
		if (!isUserLoggedIn()) {
			return Boolean.FALSE;
		}
		CheckinUserLikeEntity loggedInUser = new CheckinUserLikeEntity();
		loggedInUser.setUserId(getCurrentUserId());
		return from.getLikes().contains(loggedInUser);
	}

	public void map(List<CheckinEntity> from, List<CheckinDto> to, CheckinFilterCriteria filter) {
		for (CheckinEntity entity : from) {
			CheckinDto dto = new CheckinDto();
			mapToCheckinDto(entity, dto, filter);
			to.add(dto);
		}
	}
	
	private Integer getCurrentUserId() {
		return Integer.valueOf(jwtHelper.getUserName());
	}
	
	/**
	 * check if user is logged in or not.
	 * Throws exception if user is not logged in
	 */
	private boolean isUserLoggedIn() {
		for (String role : jwtHelper.getRoles()) {
			RoleType roleType = RoleType.getRole(role);
			if (RoleType.USER == roleType) {
				return Boolean.TRUE;
			} 
		}
		
		return Boolean.FALSE;
	}
}
