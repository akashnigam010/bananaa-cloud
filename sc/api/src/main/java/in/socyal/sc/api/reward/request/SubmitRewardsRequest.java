package in.socyal.sc.api.reward.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubmitRewardsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private List<Reward> rewards;
	private Double discount;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public List<Reward> getRewards() {
		if (this.rewards == null) {
			this.rewards = new ArrayList<>();
		}
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
