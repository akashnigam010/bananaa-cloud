package in.socyal.sc.api.type;

public enum LocalityType {
	HITECH_CITY(1, "Hitech City", "hitech-city", 1), 
	JUBILEE_HILLS(2, "Jubilee Hills", "jubilee-hills", 1), 
	BANJARA_HILLS(3, "Banjara Hills", "banjara-hills", 1), 
	GACHIBOWLI(4, "Gachibowli", "gachibowli", 1), 
	KONDAPUR(5, "Kondapur", "kondapur", 1);

	private Integer id;
	private String name;
	private String nameId;
	private Integer cityId;

	private LocalityType(Integer id, String name, String nameId, Integer cityId) {
		this.id = id;
		this.name = name;
		this.nameId = nameId;
		this.cityId = cityId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
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
	
	public static LocalityType getLocalityByNameId(String nameId) {
		for (LocalityType type : LocalityType.values()) {
			if (type.getNameId().equalsIgnoreCase(nameId)) {
				return type;
			}
		}
		return null;
	}
}
