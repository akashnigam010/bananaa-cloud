package in.socyal.sc.api.checkin.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.merchant.dto.Location;

public class ConfirmCheckinRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Location location;
	private String qrCode;
	private List<Integer> taggedUsers;
	private Boolean shareOnFb;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public List<Integer> getTaggedUsers() {
		if (this.taggedUsers == null) {
			return new ArrayList<>();
		}
		return taggedUsers;
	}

	public void setTaggedUsers(List<Integer> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public Boolean getShareOnFb() {
		if (this.shareOnFb == null) {
			this.shareOnFb = Boolean.FALSE;
		}
		return shareOnFb;
	}

	public void setShareOnFb(Boolean shareOnFb) {
		this.shareOnFb = shareOnFb;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
