package in.socyal.sc.api.rewards.dto;

import java.io.Serializable;
import java.util.Calendar;

public class RewardGivenDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer checkinId;
	private Integer rewardId;
	private Integer count;
	private Calendar rewardDateTime;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getRewardDateTime() {
		return rewardDateTime;
	}

	public void setRewardDateTime(Calendar rewardDateTime) {
		this.rewardDateTime = rewardDateTime;
	}
}
