package in.socyal.sc.api.type;

public enum SearchType {
	RESTAURANT("RESTAURANT"), 
	CUISINE("CUISINE"), 
	DISH("DISH");

	private String name;

	private SearchType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
