package in.socyal.sc.api.personnel.response;

import in.socyal.sc.api.response.GenericResponse;

public class MessageCountResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
