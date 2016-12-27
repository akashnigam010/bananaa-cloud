package in.socyal.sc.user.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.user.response.UserProfileResponse;

@Component
public class UserMapper {
	public void map(UserDto from, UserProfileResponse to) {
		to.setId(from.getId());
		to.setEmail(from.getEmail());
		to.setFacebookId(from.getFacebookId());
		to.setName(formFullName(from.getFirstName(), from.getLastName()));
		to.setPrimaryImageUrl(from.getImageUrl());
		to.setUserCheckins(1);
	}
	
	private String formFullName(String firstName, String lastName) {
		StringBuffer name = new StringBuffer();
		name.append(StringUtils.defaultString(firstName));
		name.append(" ");
		name.append(StringUtils.defaultString(lastName));
		return name.toString();
	}
}
