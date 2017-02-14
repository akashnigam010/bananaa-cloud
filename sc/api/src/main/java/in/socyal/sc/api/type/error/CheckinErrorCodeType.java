package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum CheckinErrorCodeType implements BusinessErrorCode {
	USER_NOT_FOUND(10001),
	CANCEL_CHECKIN_FAILED(10301),
	CHECKIN_ID_NOT_FOUND(10302), 
	CHECKIN_ALREADY_CANCELLED(10303),
	CHECKIN_ALREADY_APPROVED(10304),
	CHECKIN_ALREADY_CANCELLED_BY_MERCHANT(10305),
	CHECKIN_ALREADY_CANCELLED_BY_USER(10306),
	USER_CHECKIN_ALREADY_APPROVED(10309),;

	private int value;

	CheckinErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
