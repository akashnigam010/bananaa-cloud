package in.socyal.sc.helper.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum GenericErrorCodeType implements BusinessErrorCode {
	UNKNOWN_HOST_ERROR(90001), 
	REQUEST_VALIDATION_FAILED(90002),
	GENERIC_ERROR(99999);

	private int value;

	GenericErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
