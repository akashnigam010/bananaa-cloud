package in.socyal.sc.app.merchant.type;

import in.socyal.sc.helper.BusinessErrorCode;

public enum MerchantQrMappingErrorCodeType implements BusinessErrorCode {
	QR_NOT_FOUND(10201),
	QR_CODE_LOCATION_OUT_OF_RANGE(10202);

	private int value;

	MerchantQrMappingErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
