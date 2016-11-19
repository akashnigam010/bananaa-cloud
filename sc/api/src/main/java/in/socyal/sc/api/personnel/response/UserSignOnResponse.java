package in.socyal.sc.api.personnel.response;

import in.socyal.sc.api.personnel.dto.LoggedInUser;
import in.socyal.sc.api.response.GenericResponse;

public class UserSignOnResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private String token;
	private LoggedInUser user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoggedInUser getUser() {
		return user;
	}

	public void setUser(LoggedInUser user) {
		this.user = user;
	}
}
