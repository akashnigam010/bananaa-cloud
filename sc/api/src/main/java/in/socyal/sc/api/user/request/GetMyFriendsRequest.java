package in.socyal.sc.api.user.request;

import java.io.Serializable;

public class GetMyFriendsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer page;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
