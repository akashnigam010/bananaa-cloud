package in.socyal.sc.api.user.request;

import java.io.Serializable;

public class UnFollowRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
