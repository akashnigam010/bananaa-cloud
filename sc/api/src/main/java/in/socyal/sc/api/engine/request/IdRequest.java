package in.socyal.sc.api.engine.request;

import in.socyal.sc.api.request.GenericRequest;

public class IdRequest extends GenericRequest {
	private static final long serialVersionUID = 1L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
