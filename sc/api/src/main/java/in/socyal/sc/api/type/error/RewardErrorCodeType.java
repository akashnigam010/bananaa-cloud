package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum RewardErrorCodeType implements BusinessErrorCode {
	REWARD_ALREADY_GIVEN(10501);

	private int value;

	RewardErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
