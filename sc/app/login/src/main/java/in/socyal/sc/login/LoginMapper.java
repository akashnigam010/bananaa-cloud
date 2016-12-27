package in.socyal.sc.login;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.login.dto.FacebookUser;
import in.socyal.sc.api.login.dto.UserDto;

@Component
public class LoginMapper {
	private static final String GUEST_NAME = "Guest";
	private static final String GUEST_IMAGE_URL = "http://www.whitebay.in/images/guestUser.png";

	public UserDto mapFbUserToUserDto(in.socyal.sc.api.user.dto.UserDto userDetails) {
		UserDto userDto = new UserDto();
		userDto.setFirstName(userDetails.getFirstName());
		userDto.setLastName(userDetails.getLastName());
		userDto.setId(userDetails.getId());
		userDto.setImageUrl(userDetails.getImageUrl());
		return userDto;
	}

	public UserDto mapGuestUser() {
		UserDto userDto = new UserDto();
		userDto.setFirstName(GUEST_NAME);
		userDto.setImageUrl(GUEST_IMAGE_URL);
		return userDto;
	}

	public in.socyal.sc.api.login.dto.UserDto mapFbUserToUserDto(FacebookUser fbUser) {
		UserDto userDto = new UserDto();
		userDto.setFirstName(fbUser.getFirst_name());
		userDto.setLastName(fbUser.getLast_name());
		userDto.setId(Integer.parseInt(fbUser.getId()));
		userDto.setImageUrl(fbUser.getPicture().getData().getUrl());
		return userDto;
	}
}
