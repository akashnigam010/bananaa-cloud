package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum RecommendationErrorCodeType implements BusinessErrorCode {
	RCMDN_ID_NOT_FOUND(10701);

	private int value;

	RecommendationErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
