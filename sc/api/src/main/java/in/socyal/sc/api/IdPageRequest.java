package in.socyal.sc.api;

import in.socyal.sc.api.request.GenericRequest;

public class IdPageRequest extends GenericRequest {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer page;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
