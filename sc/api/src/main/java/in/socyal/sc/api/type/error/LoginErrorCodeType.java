package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum LoginErrorCodeType implements BusinessErrorCode {
	FB_ACCESS_TOKEN_NOT_FOUND(10002),
	INCORRECT_FB_TOKEN(10003),
	USER_NOT_FOUND(10004),
	EMAIL_ALREADY_REGISTERED(10005),
	REGISTERED_VIA_FB_OR_GOOGLE(10006),
	INCORRECT_PASSWORD(10007);

	private int value;

	LoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
