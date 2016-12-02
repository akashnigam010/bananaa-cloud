package in.socyal.sc.login.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum LoginErrorCodeType implements BusinessErrorCode {
	USER_ID_NOT_FOUND(10001), 
	FB_ACCESS_TOKEN_NOT_FOUND(10002),
	INCORRECT_FB_TOKEN(1003),
	USER_NOT_FOUND(10004);

	private int value;

	LoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}