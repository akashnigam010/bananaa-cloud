package in.socyal.sc.api.type;

public enum DayType {
	
	SUNDAY("Sun", "Sunday", 1),
	MONDAY("Mon", "Monday", 2),
	TUESDAY("Tue", "Tuesday", 3),
	WEDNESDAY("Wed", "Wednesday", 4),
	THURSDAY("Thu", "Thursday", 5),
	FRIDAY("Fri", "Friday", 6),
	SATURDAY("Sat", "Saturday", 7);
	
	private String abbreviation;
	private String description;
	private Integer value;

	private DayType(String abbreviation, String description, Integer value) {
		this.abbreviation = abbreviation;
		this.description = description;
		this.value = value;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public Integer getValue() {
		return value;
	}
}
