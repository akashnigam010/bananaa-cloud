package in.socyal.sc.app.merchant;

import in.socyal.sc.helper.BusinessErrorCode;

public enum MerchantQrMappingErrorCodeType implements BusinessErrorCode {
	QR_NOT_FOUND(10201);

	private int value;

	MerchantQrMappingErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
