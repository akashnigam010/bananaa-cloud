package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum MerchantQrMappingErrorCodeType implements BusinessErrorCode {
	QR_NOT_FOUND(10201),
	QR_CODE_LOCATION_OUT_OF_RANGE(10202),
	QR_CODE_DISABLED(10203);

	private int value;

	MerchantQrMappingErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
