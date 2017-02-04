package in.socyal.sc.app.checkin.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.business.response.BusinessCheckin;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinsResponse;
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
		// FIXME:We need to fetch the user checkin count from DB
		userDetailsResponse.setUserCheckins(2);
		return userDetailsResponse;
	}

	public void map(List<CheckinDto> from, GetBusinessCheckinsResponse response) {
		BusinessCheckin to = null;
		for (CheckinDto dto : from) {
			to = new BusinessCheckin();
			to.setId(dto.getId());
			to.setUser(getUserDetailsResponse(dto.getUser()));
			to.setTaggedUsers(getTaggedUserResponse(dto.getTaggedUsers()));
			if (StringUtils.isNotEmpty(dto.getRewardMessage())) {
				to.setRewardMessage(dto.getRewardMessage());
			}
			to.setTimestamp(dto.getCheckinDateTime().getTime());
			to.setCard("12");
			to.setCheckinStatus(dto.getStatus());
			response.getCheckins().add(to);
		}

	}
}
