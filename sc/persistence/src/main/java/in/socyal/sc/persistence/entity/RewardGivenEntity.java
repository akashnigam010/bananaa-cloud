package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REWARD_GIVEN", schema = "Socyal")
public class RewardGivenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CHECKIN_ID")
	private Integer checkinId;

	@Column(name = "REWARD_ID")
	private Integer rewardId;
	
	@Column(name = "COUNT")
	private Integer count;
	
	@Column(name = "REWARD_DATETIME")
	private Calendar rewardDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Calendar getRewardDateTime() {
		return rewardDateTime;
	}

	public void setRewardDateTime(Calendar rewardDateTime) {
		this.rewardDateTime = rewardDateTime;
	}
}
