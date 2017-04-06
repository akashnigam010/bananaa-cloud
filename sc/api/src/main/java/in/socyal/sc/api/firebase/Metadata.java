package in.socyal.sc.api.firebase;

public class Metadata {
	private String lastSignedInAt;
	private String createdAt;

	public String getLastSignedInAt() {
		return lastSignedInAt;
	}

	public void setLastSignedInAt(String lastSignedInAt) {
		this.lastSignedInAt = lastSignedInAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
