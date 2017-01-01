package in.socyal.sc.api.checkin.dto;

import java.util.Calendar;

import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.type.CheckinStatusType;

public class CheckinDto {
	private Integer id;
	private Integer userId;
	private MerchantDto merchant;
	private CheckinStatusType status;
	private Integer likeCount;
	private String qrCode;
	private Integer previousCheckinCount;
	private String rewardMessage;
	private Calendar checkinDateTime;
	private Calendar approvedDateTime;
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

	public MerchantDto getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDto merchant) {
		this.merchant = merchant;
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
