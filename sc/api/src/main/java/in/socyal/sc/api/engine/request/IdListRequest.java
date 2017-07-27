package in.socyal.sc.api.engine.request;

import java.util.List;

import in.socyal.sc.api.request.GenericRequest;

public class IdListRequest extends GenericRequest {
	private static final long serialVersionUID = 1L;
	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
