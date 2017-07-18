package in.socyal.sc.api;

import java.io.Serializable;

public class DetailsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String merchantNameId;
	private String itemNameId;

	public DetailsRequest() {
	}

	public DetailsRequest(String merchantNameId) {
		this.merchantNameId = merchantNameId;
	}

	public String getMerchantNameId() {
		return merchantNameId;
	}

	public void setMerchantNameId(String merchantNameId) {
		this.merchantNameId = merchantNameId;
	}

	public String getItemNameId() {
		return itemNameId;
	}

	public void setItemNameId(String itemNameId) {
		this.itemNameId = itemNameId;
	}
}
