package in.socyal.sc.api.type;

public enum CheckinStatusType {
	PENDING("PENDING"),
	APPROVED("APPROVED"),
	CANCELLED("CANCELLED");
	
	private String status;

	private CheckinStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
