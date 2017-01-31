package in.socyal.sc.api.checkin.business.request;

import java.io.Serializable;

public class BusinessApproveCheckinRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}
}
