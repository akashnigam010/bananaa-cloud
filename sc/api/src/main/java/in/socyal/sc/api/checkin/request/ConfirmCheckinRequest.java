package in.socyal.sc.api.checkin.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfirmCheckinRequest implements Serializable {
	private static final long serialVersionUID = 1L;
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
		return shareOnFb;
	}

	public void setShareOnFb(Boolean shareOnFb) {
		this.shareOnFb = shareOnFb;
	}
}
