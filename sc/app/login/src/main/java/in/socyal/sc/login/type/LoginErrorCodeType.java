package in.socyal.sc.login.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum LoginErrorCodeType implements BusinessErrorCode {
	USERNAME_NOT_FOUND(10001), 
	PASSWORD_NOT_FOUND(10002),
	CODE_NOT_FOUND(10003);

	private int value;

	LoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
