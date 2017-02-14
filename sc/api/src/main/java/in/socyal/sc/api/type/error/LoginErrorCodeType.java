package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum LoginErrorCodeType implements BusinessErrorCode {
	USER_ID_NOT_FOUND(10001), 
	FB_ACCESS_TOKEN_NOT_FOUND(10002),
	INCORRECT_FB_TOKEN(10003),
	USER_NOT_FOUND(10004),
	BUSINESS_CREDENTIALS_INVALID(10010);

	private int value;

	LoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
