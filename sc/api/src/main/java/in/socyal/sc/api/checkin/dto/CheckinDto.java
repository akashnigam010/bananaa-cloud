package in.socyal.sc.api.checkin.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.api.user.dto.UserDto;

public class CheckinDto {
	private Integer id;
	private UserDto user;
	private Integer merchantId;
	private CheckinStatusType status;
	private MerchantQrMappingDto merchantQrMapping;
	private String rewardMessage;
	private RewardStatusType rewardStatus;
	private Calendar checkinDateTime;
	private Calendar approvedDateTime;
	private Calendar updatedDateTime;
	private List<CheckinTaggedUserDto> taggedUsers;
	private Integer likeCount;
	private boolean liked;
	private FeedbackDto feedback;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public CheckinStatusType getStatus() {
		return status;
	}

	public void setStatus(CheckinStatusType status) {
		this.status = status;
	}

	public MerchantQrMappingDto getMerchantQrMapping() {
		return merchantQrMapping;
	}

	public void setMerchantQrMapping(MerchantQrMappingDto merchantQrMapping) {
		this.merchantQrMapping = merchantQrMapping;
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

	public List<CheckinTaggedUserDto> getTaggedUsers() {
		if (taggedUsers == null) {
			return new ArrayList<>();
		}
		return taggedUsers;
	}

	public void setTaggedUsers(List<CheckinTaggedUserDto> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public RewardStatusType getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(RewardStatusType rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public FeedbackDto getFeedback() {
		return feedback;
	}

	public void setFeedback(FeedbackDto feedback) {
		this.feedback = feedback;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
}
