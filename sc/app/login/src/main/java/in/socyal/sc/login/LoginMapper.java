package in.socyal.sc.login;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.login.response.LoginUserDto;

@Component
public class LoginMapper {
	private static final String GUEST_NAME = "Guest";
	private static final String GUEST_IMAGE_URL = "http://www.whitebay.in/images/guestUser.png";

	public LoginUserDto mapFbUserToUserDto(in.socyal.sc.api.user.dto.UserDto userDetails, Integer userCheckinCount) {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(userDetails.getFirstName());
		userDto.setLastName(userDetails.getLastName());
		userDto.setId(userDetails.getId());
		userDto.setImageUrl(userDetails.getImageUrl());
		userDto.setUserCheckins(userCheckinCount);
		return userDto;
	}

	public LoginUserDto mapGuestUser() {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(GUEST_NAME);
		userDto.setImageUrl(GUEST_IMAGE_URL);
		return userDto;
	}
}
