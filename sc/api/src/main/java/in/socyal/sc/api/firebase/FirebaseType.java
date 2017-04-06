package in.socyal.sc.api.firebase;

public enum FirebaseType {
	ID_TOKEN("idToken"), 
	UID("uid");

	private String value;

	FirebaseType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
