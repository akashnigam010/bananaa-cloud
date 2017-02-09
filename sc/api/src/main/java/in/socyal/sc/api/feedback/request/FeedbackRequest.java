package in.socyal.sc.api.feedback.request;

import java.io.Serializable;

public class FeedbackRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}
}
