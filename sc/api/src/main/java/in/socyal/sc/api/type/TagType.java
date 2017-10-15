package in.socyal.sc.api.type;

public enum TagType {
	VEGNONVEG("VEGNONVEG"),
	CUISINE("CUISINE"),
	SUGGESTION("SUGGESTION");

	private String name;

	private TagType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static TagType getTagByName(String name) {
		for (TagType type : TagType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
}
