package in.socyal.sc.user.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum UserErrorCodeType implements BusinessErrorCode {
	USER_NOT_AUTHORIZED(10002);

	private int value;

	UserErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
