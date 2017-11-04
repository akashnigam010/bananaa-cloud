package in.socyal.sc.api.user.request;

import in.socyal.sc.api.request.GenericRequest;

public class ProfileRequest extends GenericRequest {
	private static final long serialVersionUID = 1L;
	private String name;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
