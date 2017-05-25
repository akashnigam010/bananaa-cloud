package in.socyal.sc.api.type;

public enum CityType {
	HYDERABAD("HYDERABAD", "Hyderabad");

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

	public static CityType getCity(String name) {
		for (CityType type : CityType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
}
