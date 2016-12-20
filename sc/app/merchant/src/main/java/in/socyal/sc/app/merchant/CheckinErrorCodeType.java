package in.socyal.sc.app.merchant;

import in.socyal.sc.helper.BusinessErrorCode;

public enum CheckinErrorCodeType implements BusinessErrorCode {
	USER_NOT_FOUND(10001);

	private int value;

	CheckinErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
