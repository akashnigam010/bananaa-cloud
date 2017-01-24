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
	public void map(List<CheckinDto> from, FeedsResponse to) {
		List<Checkin> checkins = new ArrayList<>();
		for (CheckinDto dto : from) {
			Checkin checkinResponse = new Checkin();
			checkinResponse.setId(dto.getId());
			checkinResponse.setLikeCount(dto.getLikeCount());
			checkinResponse.setMerchantId(dto.getMerchant().getId());
			checkinResponse.setMerchantName(dto.getMerchant().getName());
			checkinResponse.setRating(dto.getMerchant().getRating());
			checkinResponse.setRewardMessage(dto.getRewardMessage());
			checkinResponse.setTaggedUsers(getTaggedUserResponse(dto.getTaggedUsers()));
			checkinResponse.setTimestamp(dto.getCheckinDateTime().getTime());
			checkinResponse.setUser(getUserDetailsResponse(dto.getUser()));
			checkinResponse.setHasLiked(dto.isLiked());
			checkins.add(checkinResponse);			
		}
		to.setCheckins(checkins);
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
