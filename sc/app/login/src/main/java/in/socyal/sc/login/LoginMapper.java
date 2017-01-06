package in.socyal.sc.login;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.login.dto.FacebookUser;
import in.socyal.sc.api.login.dto.LoginUserDto;

@Component
public class LoginMapper {
	private static final String GUEST_NAME = "Guest";
	private static final String GUEST_IMAGE_URL = "http://www.whitebay.in/images/guestUser.png";

	public LoginUserDto mapFbUserToUserDto(in.socyal.sc.api.user.dto.UserDto userDetails) {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(userDetails.getFirstName());
		userDto.setLastName(userDetails.getLastName());
		userDto.setId(userDetails.getId());
		userDto.setImageUrl(userDetails.getImageUrl());
		userDto.setUserCheckins(220);
		return userDto;
	}

	public LoginUserDto mapGuestUser() {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(GUEST_NAME);
		userDto.setImageUrl(GUEST_IMAGE_URL);
		return userDto;
	}

	public in.socyal.sc.api.login.dto.LoginUserDto mapFbUserToUserDto(FacebookUser fbUser) {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(fbUser.getFirst_name());
		userDto.setLastName(fbUser.getLast_name());
		userDto.setId(Integer.parseInt(fbUser.getId()));
		userDto.setImageUrl(fbUser.getPicture().getData().getUrl());
		return userDto;
	}
}
