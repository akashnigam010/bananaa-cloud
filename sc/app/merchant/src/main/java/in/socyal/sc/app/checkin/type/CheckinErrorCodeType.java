package in.socyal.sc.app.checkin.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum CheckinErrorCodeType implements BusinessErrorCode {
	USER_NOT_FOUND(10001),
	CANCEL_CHECKIN_FAILED(10301),
	CHECKIN_ID_NOT_FOUND(10302), 
	CHECKIN_ALREADY_CANCELLED(10303);

	private int value;

	CheckinErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
