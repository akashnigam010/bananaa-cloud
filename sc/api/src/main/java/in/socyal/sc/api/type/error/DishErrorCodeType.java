package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum DishErrorCodeType implements BusinessErrorCode {
	DISH_ID_NOT_FOUND(10601);

	private int value;

	DishErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
