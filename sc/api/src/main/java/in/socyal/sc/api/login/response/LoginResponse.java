package in.socyal.sc.api.login.response;

import in.socyal.sc.api.login.dto.AdditionalDetailsDto;
import in.socyal.sc.api.login.dto.LoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class LoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private LoginUserDto user;
	private String accessToken;
	private AdditionalDetailsDto additionalDetails;

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

	public AdditionalDetailsDto getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(AdditionalDetailsDto additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
}
