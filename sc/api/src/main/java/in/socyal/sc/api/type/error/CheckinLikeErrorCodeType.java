package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum CheckinLikeErrorCodeType implements BusinessErrorCode {
	//CHECKIN_ALREADY_LIKED(10307),
	//CHECKIN_NOT_LIKED(10308),
	USER_NOT_LOGGED_IN(10009);

	private int value;

	CheckinLikeErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
