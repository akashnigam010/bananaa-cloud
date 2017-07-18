package in.socyal.sc.login;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Component;

import com.restfb.types.User;

import in.socyal.sc.api.firebase.FirebaseUser;
import in.socyal.sc.api.google.GoogleUser;
import in.socyal.sc.api.login.response.FederatedUser;
import in.socyal.sc.api.login.response.LoginUserDto;
import in.socyal.sc.api.user.dto.UserDto;

@Component
public class LoginMapper {
	private static final Float DEFAULT_USER_CREDIBILITY = 2.5f;

	public LoginUserDto mapFbUserToUserDto(in.socyal.sc.api.user.dto.UserDto userDetails, Integer userCheckinCount) {
		LoginUserDto userDto = new LoginUserDto();
		userDto.setFirstName(userDetails.getFirstName());
		userDto.setLastName(userDetails.getLastName());
		userDto.setId(userDetails.getId());
		userDto.setImageUrl(userDetails.getImageUrl());
		return userDto;
	}

	public UserDto mapFirebaseUser(FirebaseUser firebaseUser) {
		UserDto user = new UserDto();
		MutablePair<String, String> names = parseDisplayName(firebaseUser.getUser().getDisplayName());
		user.setUid(firebaseUser.getUser().getUid());
		user.setFirstName(names.getLeft());
		user.setLastName(names.getRight());
		user.setNameId(generateUserNameId(user.getFirstName(), user.getLastName()));
		user.setImageUrl(firebaseUser.getUser().getPhotoURL());
		user.setEmail(firebaseUser.getUser().getEmail());
		return user;
	}

	public UserDto mapFederatedUser(FederatedUser federatedUser) {
		UserDto user = new UserDto();
		MutablePair<String, String> names = parseDisplayName(federatedUser.getName());
		user.setUid(federatedUser.getId().toString());
		user.setFirstName(names.getLeft());
		user.setLastName(names.getRight());
		user.setNameId(generateUserNameId(user.getFirstName(), user.getLastName()));
		user.setImageUrl(federatedUser.getPhotoUrl());
		user.setEmail(federatedUser.getEmail() != null ? federatedUser.getEmail() : null);
		user.setCredibility(DEFAULT_USER_CREDIBILITY);
		return user;
	}

	public LoginUserDto mapToLoginUserDto(UserDto user) {
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(user.getId());
		loginUserDto.setFirstName(user.getFirstName());
		loginUserDto.setLastName(user.getLastName());
		loginUserDto.setImageUrl(user.getImageUrl());
		loginUserDto.setNameId(user.getNameId());
		return loginUserDto;
	}

	public FederatedUser mapGoogleUser(GoogleUser googleUser) {
		FederatedUser user = new FederatedUser();
		user.setId(googleUser.getId());
		user.setEmail(googleUser.getEmail());
		user.setName(googleUser.getName());
		user.setPhotoUrl(googleUser.getPicture());
		return user;
	}

	public FederatedUser mapFacebookUser(User fbUser) {
		FederatedUser user = new FederatedUser();
		user.setId(fbUser.getId());
		user.setEmail(fbUser.getEmail());
		user.setName(fbUser.getFirstName() + " " + fbUser.getLastName());
		user.setPhotoUrl(fbUser.getPicture().getUrl());
		return user;

	}

	private MutablePair<String, String> parseDisplayName(String displayName) {
		MutablePair<String, String> names = new MutablePair<>();
		String[] nameString = displayName.split(" ");
		StringBuilder firstName = new StringBuilder();
		int i;
		for (i = 0; i < nameString.length - 1; i++) {
			firstName.append(nameString[i] + " ");
		}
		names.setLeft(firstName.toString().trim());
		names.setRight(nameString[i]);
		return names;
	}

	/**
	 * Generates unique nameId for user
	 * 
	 * @param dto
	 * @param cal
	 * @return
	 */
	private String generateUserNameId(String firstName, String lastName) {
		StringBuilder nameId = new StringBuilder();
		if (StringUtils.isNotBlank(firstName)) {
			nameId.append(firstName.toLowerCase().trim() + "-");
		}
		if (StringUtils.isNotBlank(lastName)) {
			nameId.append(lastName.toLowerCase().trim() + "-");
		}
		nameId.append(Calendar.getInstance().getTimeInMillis());
		return nameId.toString();
	}
}
