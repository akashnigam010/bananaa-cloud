package in.socyal.sc.api.reward.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class SubmitRewardsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private Boolean showReward;
	private String rewardMessage;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public Boolean getShowReward() {
		return showReward;
	}

	public void setShowReward(Boolean showReward) {
		this.showReward = showReward;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}

	public void setRewardMessage(String rewardMessage) {
		this.rewardMessage = rewardMessage;
	}
}
