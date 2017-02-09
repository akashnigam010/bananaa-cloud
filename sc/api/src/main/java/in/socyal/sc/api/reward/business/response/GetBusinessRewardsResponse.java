package in.socyal.sc.api.reward.business.response;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class GetBusinessRewardsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<RewardsResponse> rewards;

	public List<RewardsResponse> getRewards() {
		if (rewards == null) {
			return Collections.emptyList();
		}
		return rewards;
	}

	public void setRewards(List<RewardsResponse> rewards) {
		this.rewards = rewards;
	}
}
