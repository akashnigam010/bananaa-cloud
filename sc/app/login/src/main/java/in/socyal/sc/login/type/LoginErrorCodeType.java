package in.socyal.sc.login.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum LoginErrorCodeType implements BusinessErrorCode {
	USERNAME_EMPTY(10001), 
	PASSWORD_EMPTY(10001);

	private int value;

	LoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
