package in.socyal.sc.api.checkin.business.request;

import java.io.Serializable;

public class GetBusinessCheckinHistoryRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer page;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
