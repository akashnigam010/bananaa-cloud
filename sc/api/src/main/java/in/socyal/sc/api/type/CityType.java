package in.socyal.sc.api.type;

public enum CityType {
	HYDERABAD("HYDERABAD", "hyderabad");

	private String label;
	private String name;

	private CityType(String label, String name) {
		this.label = label;
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}
}
