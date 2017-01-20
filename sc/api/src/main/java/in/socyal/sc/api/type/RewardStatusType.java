package in.socyal.sc.api.type;

public enum RewardStatusType {
	GIVEN("GIVEN"),
	ACKNOWLEDGED("ACKNOWLEDGED");
	
	private String status;

	private RewardStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
