package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum GenericErrorCodeType implements BusinessErrorCode {
	UNKNOWN_HOST_ERROR(90001), 
	REQUEST_VALIDATION_FAILED(90002),
	JWT_TOKEN_EXPIRED(90003),
	LOGIN_REQUIRED(90004),
	INVALID_TOKEN(90005),
	GENERIC_ERROR(99999);

	private int value;

	GenericErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
