package in.socyal.sc.api.user.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.login.response.LoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class SearchFriendToTagResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<LoginUserDto> users;

	public List<LoginUserDto> getUsers() {
		if (this.users == null) {
			this.users = new ArrayList<>();
		}
		return users;
	}

	public void setUsers(List<LoginUserDto> users) {
		this.users = users;
	}
}
