package in.socyal.sc.api.user.request;

import java.io.Serializable;

public class SaveRegistrationIdRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String registrationId;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
