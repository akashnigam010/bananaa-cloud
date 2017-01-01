package in.socyal.sc.api.checkin.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class ValidateCheckinResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String merchantName;
	private String shortAddress;
	private Integer previousCheckinCount;

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
}
