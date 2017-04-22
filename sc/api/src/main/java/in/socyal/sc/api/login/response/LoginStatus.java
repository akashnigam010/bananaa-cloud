package in.socyal.sc.api.login.response;

import java.util.ResourceBundle;

public class LoginStatus {
	private static final String ENVIRONMENT = "bna.env";
	private ResourceBundle resource = ResourceBundle.getBundle("environment");
	
	private String firstName;
	private String nameId;
	private Boolean status = Boolean.FALSE;
	private String bnaEnv = resource.getString(ENVIRONMENT);

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

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getBnaEnv() {
		return bnaEnv;
	}

	public void setBnaEnv(String bnaEnv) {
		this.bnaEnv = bnaEnv;
	}
	
	public static void main(String[] args) {
		LoginStatus loginStatus = new LoginStatus();
		System.out.println(loginStatus.getBnaEnv());
	}
}
