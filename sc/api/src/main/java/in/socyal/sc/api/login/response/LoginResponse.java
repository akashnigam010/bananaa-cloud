package in.socyal.sc.api.login.response;

import in.socyal.sc.api.login.dto.LoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class LoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private LoginUserDto user;
	private String accessToken;

	public LoginUserDto getUser() {
		return user;
	}

	public void setUser(LoginUserDto user) {
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
