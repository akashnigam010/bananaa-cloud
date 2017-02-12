package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum MerchantErrorCodeType implements BusinessErrorCode {
	MERCHANTS_NOT_FOUND(10101),
	MERCHANT_DETAILS_NOT_FOUND(10102);

	private int value;

	MerchantErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
