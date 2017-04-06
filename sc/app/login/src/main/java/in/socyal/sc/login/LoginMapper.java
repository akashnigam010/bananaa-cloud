package in.socyal.sc.login;

import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.firebase.FirebaseUser;
import in.socyal.sc.api.login.response.LoginUserDto;
import in.socyal.sc.api.user.dto.UserDto;

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
		return userDto;
	}

	public LoginUserDto mapGuestUser() {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(GUEST_NAME);
		userDto.setImageUrl(GUEST_IMAGE_URL);
		return userDto;
	}

	public UserDto mapFirebaseUser(FirebaseUser firebaseUser) {
		UserDto user = new UserDto();
		MutablePair<String, String> names = parseDisplayName(firebaseUser.getUser().getDisplayName());
		user.setUid(firebaseUser.getUser().getUid());
		user.setFirstName(names.getLeft());
		user.setLastName(names.getRight());
		user.setImageUrl(firebaseUser.getUser().getPhotoURL());
		user.setEmail(firebaseUser.getUser().getEmail());
		return user;
	}

	private  MutablePair<String, String> parseDisplayName(String displayName) {
		MutablePair<String, String> names = new MutablePair<>();
		String[] nameString = displayName.split(" ");
		StringBuilder firstName = new StringBuilder();
		int i;
		for (i = 0; i < nameString.length - 1; i++) {
			firstName.append(nameString[i]+" ");
		}
		names.setLeft(firstName.toString().trim());
		names.setRight(nameString[i]);
		return names;
	}

	public LoginUserDto mapToLoginUserDto(UserDto user) {
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(user.getId());
		loginUserDto.setFirstName(user.getFirstName());
		loginUserDto.setLastName(user.getLastName());
		loginUserDto.setImageUrl(user.getImageUrl());
		return loginUserDto;
	}
}
