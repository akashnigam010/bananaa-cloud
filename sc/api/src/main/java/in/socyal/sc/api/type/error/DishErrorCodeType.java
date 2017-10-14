package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum DishErrorCodeType implements BusinessErrorCode {
	DISH_ID_NOT_FOUND(10601),
	DISH_DETAILS_NOT_FOUND(10602),
	CAN_NOT_ADD_EDIT_DISH(10603);

	private int value;

	DishErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
