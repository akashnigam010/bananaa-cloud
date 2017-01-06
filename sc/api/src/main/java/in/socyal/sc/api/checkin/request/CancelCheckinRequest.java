package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

public class CancelCheckinRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
