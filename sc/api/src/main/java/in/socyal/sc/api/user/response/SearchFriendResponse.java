package in.socyal.sc.api.user.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.login.dto.LoginUserDto;
import in.socyal.sc.api.response.GenericResponse;

public class SearchFriendResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<LoginUserDto> friends;
	private List<LoginUserDto> people;

	public List<LoginUserDto> getFriends() {
		if (this.friends == null) {
			this.friends = new ArrayList<>();
		}
		return friends;
	}

	public void setFriends(List<LoginUserDto> friends) {
		this.friends = friends;
	}

	public List<LoginUserDto> getPeople() {
		if (this.people == null) {
			this.people = new ArrayList<>();
		}
		return people;
	}

	public void setPeople(List<LoginUserDto> people) {
		this.people = people;
	}

}
