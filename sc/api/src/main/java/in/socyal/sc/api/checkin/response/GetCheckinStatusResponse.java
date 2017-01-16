package in.socyal.sc.api.checkin.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.type.CheckinStatusType;

public class GetCheckinStatusResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private String merchantName;
	private Integer merchantId;
	private String shortAddress;
	private Integer previousCheckinCount;
	private Integer newCheckinCount;
	private CheckinStatusType checkinStatus;
	private String cancelMessage;
	private List<TaggedUserResponse> taggedUsers;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public Integer getNewCheckinCount() {
		return newCheckinCount;
	}

	public void setNewCheckinCount(Integer newCheckinCount) {
		this.newCheckinCount = newCheckinCount;
	}

	public CheckinStatusType getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(CheckinStatusType checkinStatus) {
		this.checkinStatus = checkinStatus;
	}

	public List<TaggedUserResponse> getTaggedUsers() {
		if (taggedUsers == null) {
			return new ArrayList<>();
		}
		return taggedUsers;
	}

	public void setTaggedUsers(List<TaggedUserResponse> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public Integer getPreviousCheckinCount() {
		return previousCheckinCount;
	}

	public void setPreviousCheckinCount(Integer previousCheckinCount) {
		this.previousCheckinCount = previousCheckinCount;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getCancelMessage() {
		return cancelMessage;
	}

	public void setCancelMessage(String cancelMessage) {
		this.cancelMessage = cancelMessage;
	}
}
