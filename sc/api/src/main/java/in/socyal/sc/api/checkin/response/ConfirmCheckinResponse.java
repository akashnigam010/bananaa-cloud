package in.socyal.sc.api.checkin.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class ConfirmCheckinResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private String merchantName;
	private String shortAddress;
	private Integer previousCheckinCount;
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

	public Integer getPreviousCheckinCount() {
		return previousCheckinCount;
	}

	public void setPreviousCheckinCount(Integer previousCheckinCount) {
		this.previousCheckinCount = previousCheckinCount;
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
}
