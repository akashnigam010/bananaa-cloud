package in.socyal.sc.app.merchant;

import in.socyal.sc.helper.BusinessErrorCode;

public enum CheckinLikeErrorCodeType implements BusinessErrorCode {
	CHECKIN_ALREADY_LIKED(10304),
	CHECKIN_NOT_LIKED(10305),
	USER_NOT_LOGGED_IN(10009);

	private int value;

	CheckinLikeErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
