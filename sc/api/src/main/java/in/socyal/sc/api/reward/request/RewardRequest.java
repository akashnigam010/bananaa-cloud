package in.socyal.sc.api.reward.request;

import java.io.Serializable;

public class RewardRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}
}
