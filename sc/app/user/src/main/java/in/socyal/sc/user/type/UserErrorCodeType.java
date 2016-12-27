package in.socyal.sc.user.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum UserErrorCodeType implements BusinessErrorCode {
	LOGIN_REQUIRED(10005);

	private int value;

	UserErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
