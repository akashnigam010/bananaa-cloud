package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

public class ProfileFeedsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer page;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
