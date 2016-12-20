package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.socyal.sc.api.type.CheckinStatusType;

@Entity
@Table(name = "CHECKIN", schema = "Socyal")
public class CheckinEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "MERCHANT_ID")
	private Integer merchantId;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private CheckinStatusType status;

	@Column(name = "LIKE_COUNT")
	private Integer likeCount;
	
	@Column(name = "QR_CODE")
	private String qrCode;
	
	@Column(name = "PREVIOUS_CHECKIN_COUNT")
	private Integer previousCheckinCount;
	
	@Column(name = "REWARD_MESSAGE")
	private String rewardMessage;
	
	@Column(name = "CHECKIN_DATETIME")
	private Calendar checkinDateTime;
	
	@Column(name = "APPROVED_DATETIME")
	private Calendar approvedDateTime;
	
	@Column(name = "UPDATED_DATETIME")
	private Calendar updatedDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public CheckinStatusType getStatus() {
		return status;
	}

	public void setStatus(CheckinStatusType status) {
		this.status = status;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Integer getPreviousCheckinCount() {
		return previousCheckinCount;
	}

	public void setPreviousCheckinCount(Integer previousCheckinCount) {
		this.previousCheckinCount = previousCheckinCount;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}

	public void setRewardMessage(String rewardMessage) {
		this.rewardMessage = rewardMessage;
	}

	public Calendar getCheckinDateTime() {
		return checkinDateTime;
	}

	public void setCheckinDateTime(Calendar checkinDateTime) {
		this.checkinDateTime = checkinDateTime;
	}

	public Calendar getApprovedDateTime() {
		return approvedDateTime;
	}

	public void setApprovedDateTime(Calendar approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

	public Calendar getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Calendar updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
}
