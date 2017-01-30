package in.socyal.sc.api.type;

public enum FeedbackStatusType {
	ASKED("ASKED"),
	NOT_ASKED("NOTASKED"),
	RECEIVED("RECEIVED");
	
	private String status;

	private FeedbackStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
