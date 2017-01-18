package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

public class MyFeedsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer page;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
