package in.socyal.sc.api.feedback.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class FeedbackStatusResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private String merchantName;
	private String shortAddress;
	private Boolean showFeedback;

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

	public Boolean getShowFeedback() {
		return showFeedback;
	}

	public void setShowFeedback(Boolean showFeedback) {
		this.showFeedback = showFeedback;
	}
}
