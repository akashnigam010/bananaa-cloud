package in.socyal.sc.api.merchant.business.request;

import java.io.Serializable;

public class SaveBusinessRegistrationIdRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String registrationId;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
