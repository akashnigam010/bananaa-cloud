package in.socyal.sc.api.user.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.login.response.LoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class FriendResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<LoginUserDto> friends;

	public List<LoginUserDto> getFriends() {
		if (this.friends == null) {
			this.friends = new ArrayList<>();
		}
		return friends;
	}

	public void setFriends(List<LoginUserDto> friends) {
		this.friends = friends;
	}

}
