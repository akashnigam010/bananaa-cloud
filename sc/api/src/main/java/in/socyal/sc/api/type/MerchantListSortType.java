package in.socyal.sc.api.type;

public enum MerchantListSortType {
	//Add the code value same as the entity field names
	//So that orderby happens based on the entity field values
	RATING("rating"),
	PROMOTION("promotion"),
	DISTANCE("distance");
	
	private String code;

	private MerchantListSortType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
