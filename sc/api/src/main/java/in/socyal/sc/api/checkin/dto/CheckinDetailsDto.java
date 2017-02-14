package in.socyal.sc.api.checkin.dto;

import java.util.Calendar;

import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;

public class CheckinDetailsDto {
	private Integer userId;
	private Integer merchantId;
	private String qrCode;
	private CheckinStatusType status;
	private RewardStatusType rewardStatus;
	private FeedbackStatusType feedbackStatus;
	private Calendar checkinDateTime;
	private Calendar approvedDateTime;
	private Calendar updatedDateTime;

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

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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

	public RewardStatusType getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(RewardStatusType rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public FeedbackStatusType getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(FeedbackStatusType feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
}
