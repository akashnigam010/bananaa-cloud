package in.socyal.sc.api.login.response;

public class LoginStatus {
	private String firstName;
	private Boolean status = Boolean.FALSE;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
