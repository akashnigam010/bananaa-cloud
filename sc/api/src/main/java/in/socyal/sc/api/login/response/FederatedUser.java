package in.socyal.sc.api.login.response;

public class FederatedUser {
	private String id;
	private String name;
	private String email;
	private String photoUrl;
	private boolean isFederatedLogin = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public boolean isFederatedLogin() {
		return isFederatedLogin;
	}

	public void setFederatedLogin(boolean isFederatedLogin) {
		this.isFederatedLogin = isFederatedLogin;
	}
}
