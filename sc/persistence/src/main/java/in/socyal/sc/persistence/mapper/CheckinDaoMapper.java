package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
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
	
	public void map(CheckinDetailsDto from, CheckinEntity to) {
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantEntity merchant = new MerchantEntity();
		merchant.setId(from.getMerchantId());
		to.setMerchant(merchant);
		MerchantQrMappingEntity qrMapping = new MerchantQrMappingEntity();
		qrMapping.setQrCode(from.getQrCode());
		to.setMerchantQrMapping(qrMapping);
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		UserEntity user = new UserEntity();
		user.setId(from.getUserId());
		to.setUser(user);
		to.setRewardStatus(from.getRewardStatus());
		FeedbackEntity feedback = new FeedbackEntity();
		feedback.setUserId(from.getUserId());
		feedback.setMerchantId(from.getMerchantId());
		to.setFeedback(feedback);
	}
	
	public void map(CheckinEntity from, CheckinDto to) {
		to.setId(from.getId());
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantDto merchant = new MerchantDto();
		merchantMapper.map(from.getMerchant(), merchant, false);
		to.setMerchant(merchant);
		MerchantQrMappingDto qrMappingDto = new MerchantQrMappingDto();
		qrCodeMapper.map(from.getMerchantQrMapping(), qrMappingDto);
		to.setMerchantQrMapping(qrMappingDto);
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		if (StringUtils.isNotEmpty(from.getRewardMessage())) {
			to.setRewardMessage(from.getRewardMessage());
		}		
		UserDto user = new UserDto();
		userDaoMapper.map(from.getUser(), user);
		to.setUser(user);
		List<CheckinTaggedUserDto> taggedUsers = new ArrayList<>();
		for (CheckinTaggedUserEntity taggedUser : from.getTaggedUsers()) {
			CheckinTaggedUserDto taggedUserDto = new CheckinTaggedUserDto();
			taggedUserMapper.map(taggedUser, taggedUserDto);
			taggedUsers.add(taggedUserDto);
		}
		to.setTaggedUsers(taggedUsers);
		to.setLiked(hasLiked(from));
		to.setLikeCount(from.getLikes().size());
		to.setRewardStatus(from.getRewardStatus());
		FeedbackDto feedback = new FeedbackDto();
		feedbackDaoMapper.map(from.getFeedback(), feedback);
		to.setFeedback(feedback);
	}
	
	private boolean hasLiked(CheckinEntity from) {
		if (!isUserLoggedIn()) {
			return Boolean.FALSE;
		}
		CheckinUserLikeEntity loggedInUser = new CheckinUserLikeEntity();
		loggedInUser.setUserId(getCurrentUserId());
		return from.getLikes().contains(loggedInUser);
	}

	public void map(List<CheckinEntity> from, List<CheckinDto> to) {
		for (CheckinEntity entity : from) {
			CheckinDto dto = new CheckinDto();
			map(entity, dto);
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
