package in.socyal.sc.api.reward.business.response;

import java.io.Serializable;

public class RewardsResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer merchantId;
	private String reward;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
}
