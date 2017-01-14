package in.socyal.sc.persistence.mapper;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.restfb.types.User;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class UserDaoMapper {
	public void map(UserEntity from, UserDto to) {
		if (StringUtils.isNotEmpty(from.getEmail())) {
			to.setEmail(from.getEmail());
		}
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setId(from.getId());
		to.setImageUrl(from.getImageUrl());
		to.setFacebookId(from.getFacebookId());
		to.setFacebookLink(from.getFacebookLink());
		to.setFacebookToken(from.getFacebookToken());
		to.setGender(from.getGender());
		to.setCreatedDateTime(from.getCreatedDateTime());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
	}

	public void map(User from, UserEntity to, String fbAccessToken) {
		if (StringUtils.isNotEmpty(from.getEmail())) {
			to.setEmail(from.getEmail());
		}
		to.setFacebookId(from.getId());
		to.setFacebookLink(from.getLink());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setGender(from.getGender());
		to.setImageUrl(from.getPicture().getUrl());
		to.setFacebookToken(fbAccessToken);
		to.setCreatedDateTime(Calendar.getInstance());
	}

	public void map(UserDto from, UserEntity to, String fbAccessToken) {
		to.setId(from.getId());
		if (StringUtils.isNotEmpty(from.getEmail())) {
			to.setEmail(from.getEmail());
		}
		to.setFacebookId(from.getFacebookId());
		to.setFacebookLink(from.getFacebookLink());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setGender(from.getGender());
		to.setImageUrl(from.getImageUrl());
		to.setFacebookToken(fbAccessToken);
	}

	public void map(List<UserEntity> users, List<UserDto> userDtos) {
		for (UserEntity user : users) {
			UserDto userDto = new UserDto();
			map(user, userDto);
			userDtos.add(userDto);
		}
	}
}
