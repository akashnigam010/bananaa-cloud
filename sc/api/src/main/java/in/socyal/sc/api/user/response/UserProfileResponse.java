package in.socyal.sc.api.user.response;

import java.io.Serializable;

import in.socyal.sc.api.login.dto.LoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class UserProfileResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private LoginUserDto user;

	public LoginUserDto getUser() {
		return user;
	}

	public void setUser(LoginUserDto user) {
		this.user = user;
	}
}
