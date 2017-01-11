package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.checkin.dto.CheckinUserLikeDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;
import in.socyal.sc.persistence.entity.CheckinUserLikeEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;

@Component
public class CheckinDaoMapper {
	@Autowired MerchantDaoMapper mapper;
	@Autowired CheckinTaggedUserMapper taggedUserMapper;
	@Autowired CheckinUserLikeMapper likeMapper;
	
	public void map(CheckinDetailsDto from, CheckinEntity to) {
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantEntity merchant = new MerchantEntity();
		merchant.setId(from.getMerchantId());
		to.setMerchant(merchant);
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setUserId(from.getUserId());
	}
	
	public void map(CheckinEntity from, CheckinDto to) {
		to.setId(from.getId());
		to.setCheckinDateTime(from.getCheckinDateTime());
		MerchantDto merchant = new MerchantDto();
		mapper.map(from.getMerchant(), merchant);
		to.setMerchant(merchant);
		to.setQrCode(from.getQrCode());
		to.setStatus(from.getStatus());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
		to.setUserId(from.getUserId());
		List<CheckinTaggedUserDto> taggedUsers = new ArrayList<>();
		for (CheckinTaggedUserEntity taggedUser : from.getTaggedUsers()) {
			CheckinTaggedUserDto taggedUserDto = new CheckinTaggedUserDto();
			taggedUserMapper.map(taggedUser, taggedUserDto);
			taggedUsers.add(taggedUserDto);
		}
		to.setTaggedUsers(taggedUsers);
		
		List<CheckinUserLikeDto> likes = new ArrayList<>();
		for (CheckinUserLikeEntity taggedUser : from.getLikes()) {
			CheckinUserLikeDto likeDto = new CheckinUserLikeDto();
			likeMapper.map(taggedUser, likeDto);
			likes.add(likeDto);
		}
		to.setLikes(likes);
	}
}
