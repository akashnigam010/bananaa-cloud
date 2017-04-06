package in.socyal.sc.api.login.response;

import in.socyal.sc.api.response.GenericResponse;

public class BusinessLoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private BusinessLoginUserDto user;
	private String accessToken;
	private String supportNumber;

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

	public String getSupportNumber() {
		return supportNumber;
	}

	public void setSupportNumber(String supportNumber) {
		this.supportNumber = supportNumber;
	}
}
