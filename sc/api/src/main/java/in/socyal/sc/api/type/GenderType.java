package in.socyal.sc.api.type;

public enum GenderType {
	MALE("M"), 
	FEMALE("F"), 
	OTHER("O");

	private String value;

	private GenderType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
