package in.socyal.sc.api.merchant.request;

import java.io.Serializable;

public class MerchantDetailsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nameId;

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
}
