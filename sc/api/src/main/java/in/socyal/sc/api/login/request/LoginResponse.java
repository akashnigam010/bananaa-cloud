package in.socyal.sc.api.login.request;

import in.socyal.sc.api.login.dto.UserDto;
import in.socyal.sc.api.response.GenericResponse;

public class LoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private UserDto user;
	private String accessToken;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
