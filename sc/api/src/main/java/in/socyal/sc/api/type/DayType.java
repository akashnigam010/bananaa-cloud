package in.socyal.sc.api.type;

public enum DayType {
	MONDAY("Mon", "Monday"),
	TUESDAY("Tue", "Tuesday"),
	WEDNESDAY("Wed", "Wednesday"),
	THURSDAY("Thu", "Thursday"),
	FRIDAY("Fri", "Friday"),
	SATURDAY("Sat", "Saturday"),
	SUNDAY("Sun", "Sunday");
	
	private String code;
	private String value;

	private DayType(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
}
