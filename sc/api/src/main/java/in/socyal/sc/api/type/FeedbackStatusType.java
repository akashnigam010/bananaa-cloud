package in.socyal.sc.api.type;

public enum FeedbackStatusType {
	ASKED("ASKED"),
	DISMISSED("DISMISSED"),
	SUBMITTED("SUBMITTED");
	
	private String status;

	private FeedbackStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
