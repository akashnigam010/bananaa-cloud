package in.socyal.sc.api.user.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.login.dto.UserDto;
import in.socyal.sc.api.response.GenericResponse;

public class SearchFriendResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<UserDto> users;

	public List<UserDto> getUsers() {
		if (this.users == null) {
			this.users = new ArrayList<>();
		}
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

}
