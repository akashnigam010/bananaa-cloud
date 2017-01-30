package in.socyal.sc.api.type;

public enum CheckinStatusType {
	PENDING("PENDING"),
	APPROVED("APPROVED"),
	USER_CANCELLED("USERCANCELLED"),
	MERCHANT_CANCELLED("MERCHANTCANCELLED");
	
	private String status;

	private CheckinStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
