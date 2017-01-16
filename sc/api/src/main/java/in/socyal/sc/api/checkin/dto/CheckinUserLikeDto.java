package in.socyal.sc.api.checkin.dto;

import java.io.Serializable;

public class CheckinUserLikeDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer checkinId;
	private Integer userId;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
