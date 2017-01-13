package in.socyal.sc.api.checkin.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.user.dto.UserDto;

public class CheckinDto {
	private Integer id;
	private UserDto user;
	private MerchantDto merchant;
	private CheckinStatusType status;
	/*private Integer likeCount;*/
	private String qrCode;
	/*private Integer previousCheckinCount;*/
	private String rewardMessage;
	private Calendar checkinDateTime;
	private Calendar approvedDateTime;
	private Calendar updatedDateTime;
	private List<CheckinTaggedUserDto> taggedUsers;
	private Integer likeCount;
	private boolean liked;

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

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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
}
