package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.user.dto.UserDto;

public class UserDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private UserDto user;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
