package in.socyal.sc.user.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum UserErrorCodeType implements BusinessErrorCode {
	USER_NOT_FOUND(10001),
	LOGIN_REQUIRED(10005),
	USER_CANNOT_FOLLOW_HIMSELF(10006), 
	USER_ALREADY_FOLLOWING(10007), 
	USER_NOT_FOLLOWING(10008);

	private int value;

	UserErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
