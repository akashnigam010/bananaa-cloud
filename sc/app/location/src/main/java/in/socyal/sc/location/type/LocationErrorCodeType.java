package in.socyal.sc.location.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum LocationErrorCodeType implements BusinessErrorCode {
	SEARCH_STRING_NOT_FOUND(10001);

	private int value;

	LocationErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
