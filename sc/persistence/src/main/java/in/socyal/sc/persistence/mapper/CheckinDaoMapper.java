package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;
import in.socyal.sc.persistence.entity.CheckinUserLikeEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class CheckinDaoMapper {
	@Autowired MerchantDaoMapper mapper;
	@Autowired CheckinTaggedUserMapper taggedUserMapper;
	@Autowired CheckinUserLikeMapper likeMapper;
	@Autowired UserDaoMapper userDaoMapper;
	@Autowired JwtTokenHelper jwtHelper;
	
	public void map(CheckinDetailsDto from, CheckinEntity to) {
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantEntity merchant = new MerchantEntity();
		merchant.setId(from.getMerchantId());
		to.setMerchant(merchant);
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		UserEntity user = new UserEntity();
		user.setId(from.getUserId());
		to.setUser(user);
	}
	
	public void map(CheckinEntity from, CheckinDto to) {
		to.setId(from.getId());
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantDto merchant = new MerchantDto();
		//Mapping only merchant name and id in a checkin
		//mapper.map(from.getMerchant(), merchant);
		merchant.setId(from.getMerchant().getId());
		merchant.setName(from.getMerchant().getName());
		to.setMerchant(merchant);
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
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
		
// 		Removed linear search algo; will be too slow if number of likes increase.
//		for (CheckinUserLikeEntity likedUser : from.getLikes().con) {
//			//Logic for checking whether user has liked this checkin or not
//			if (getCurrentUserId() == likedUser.getUserId()) {
//				to.setLiked(Boolean.TRUE);
//				break;
//			}
//		}
		
	}
	
	private boolean hasLiked(CheckinEntity from) {
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
}
