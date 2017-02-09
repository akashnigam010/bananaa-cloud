package in.socyal.sc.api.type;

public enum RewardStatusType {
	GIVEN("GIVEN"),
	NOT_GIVEN("NOTGIVEN");
	
	private String status;

	private RewardStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
