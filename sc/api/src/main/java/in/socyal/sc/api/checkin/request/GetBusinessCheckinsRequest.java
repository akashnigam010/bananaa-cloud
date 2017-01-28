package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

public class GetBusinessCheckinsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer dateIdentifier;
	private Integer page;

	public Integer getDateIdentifier() {
		return dateIdentifier;
	}

	public void setDateIdentifier(Integer dateIdentifier) {
		this.dateIdentifier = dateIdentifier;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
