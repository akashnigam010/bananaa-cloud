package in.socyal.sc.api.type;

public enum LocalityType {
	HITECH_CITY(1, "Hitech City", 1), 
	JUBILEE_HILLS(2, "Jubilee Hills", 1), 
	BANJARA_HILLS(3, "Banjara Hills", 1), 
	GACHIBOWLI(4, "Gachibowli", 1), 
	KONDAPUR(5, "Kondapur", 1);

	private Integer id;
	private String name;
	private Integer cityId;

	private LocalityType(Integer id, String name, Integer cityId) {
		this.id = id;
		this.name = name;
		this.cityId = cityId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCityId() {
		return cityId;
	}

	public static LocalityType getLocalityByName(String name) {
		for (LocalityType type : LocalityType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}

	public static LocalityType getLocalityById(Integer id) {
		for (LocalityType type : LocalityType.values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		return null;
	}
}
