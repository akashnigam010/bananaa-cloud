package in.socyal.sc.api.login.response;

import in.socyal.sc.api.login.dto.BusinessLoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class BusinessLoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private BusinessLoginUserDto user;
	private String accessToken;

	public BusinessLoginUserDto getUser() {
		return user;
	}

	public void setUser(BusinessLoginUserDto user) {
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
