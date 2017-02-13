package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum MerchantLoginErrorCodeType implements BusinessErrorCode {
	MERCHANT_DEVICE_DETAILS_FOUND(10103);

	private int value;

	MerchantLoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
