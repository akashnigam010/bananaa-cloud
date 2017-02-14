package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

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
