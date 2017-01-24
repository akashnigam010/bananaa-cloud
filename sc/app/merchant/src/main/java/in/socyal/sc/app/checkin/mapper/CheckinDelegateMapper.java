package in.socyal.sc.app.checkin.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinTaggedUserDto;
import in.socyal.sc.api.checkin.response.Checkin;
import in.socyal.sc.api.checkin.response.FeedsResponse;
import in.socyal.sc.api.checkin.response.TaggedUserResponse;
import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.user.dto.UserDto;

@Component
public class CheckinDelegateMapper {
	public void map(List<CheckinDto> from, FeedsResponse to, int page) {
		List<Checkin> merchantCheckins = new ArrayList<>();
		for (CheckinDto dto : from) {
			Checkin merchantCheckin = new Checkin();
			merchantCheckin.setId(dto.getId());
			merchantCheckin.setLikeCount(dto.getLikeCount());
			merchantCheckin.setMerchantId(dto.getMerchant().getId());
			merchantCheckin.setMerchantName(dto.getMerchant().getName());
			merchantCheckin.setRating(dto.getMerchant().getRating());
			merchantCheckin.setRewardMessage(dto.getRewardMessage());
			merchantCheckin.setTaggedUsers(getTaggedUserResponse(dto.getTaggedUsers()));
			merchantCheckin.setTimestamp(dto.getCheckinDateTime().getTime());
			merchantCheckin.setUser(getUserDetailsResponse(dto.getUser()));
			merchantCheckin.setHasLiked(dto.isLiked());
			merchantCheckins.add(merchantCheckin);			
		}
		to.setCheckins(merchantCheckins);
	}
	
	private List<TaggedUserResponse> getTaggedUserResponse(List<CheckinTaggedUserDto> list) {
		List<TaggedUserResponse> taggedUserResponse = new ArrayList<>();
		for (CheckinTaggedUserDto taggedUserDto : list) {
			TaggedUserResponse taggedUser = new TaggedUserResponse();
			taggedUser.setId(taggedUserDto.getId());
			taggedUser.setName(taggedUserDto.getUser().getName());
			taggedUserResponse.add(taggedUser);
		}
		return taggedUserResponse;
	}
	
	private UserDetailsResponse getUserDetailsResponse(UserDto userDto) {
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		userDetailsResponse.setId(userDto.getId());
		userDetailsResponse.setImageUrl(userDto.getImageUrl());
		userDetailsResponse.setName(userDto.getName());
		//FIXME:We need to fetch the user checkin count from DB
		userDetailsResponse.setUserCheckins(2);
		return userDetailsResponse;
	}
}
