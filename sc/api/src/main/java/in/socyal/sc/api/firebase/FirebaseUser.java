package in.socyal.sc.api.firebase;

public class FirebaseUser {
	private User user;
	private boolean status;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
