package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum UserErrorCodeType implements BusinessErrorCode {
	USER_NOT_LOGGED_IN(10009);

	private int value;

	UserErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
