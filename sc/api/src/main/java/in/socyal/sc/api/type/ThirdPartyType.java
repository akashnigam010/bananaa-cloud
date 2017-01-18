package in.socyal.sc.api.type;

public enum ThirdPartyType {
	FACEBOOK("Facebook");

	private String name;

	ThirdPartyType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
