package in.socyal.sc.api.checkin.dto;

import java.io.Serializable;

import in.socyal.sc.api.user.dto.UserDto;

public class CheckinUserLikeDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer checkinId;
	private UserDto user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
