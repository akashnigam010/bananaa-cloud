package in.socyal.sc.app.merchant.type;

import in.socyal.sc.helper.BusinessErrorCode;

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
