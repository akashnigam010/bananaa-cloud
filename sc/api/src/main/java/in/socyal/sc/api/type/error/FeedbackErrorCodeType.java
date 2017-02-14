package in.socyal.sc.api.type.error;

import in.socyal.sc.api.helper.BusinessErrorCode;

public enum FeedbackErrorCodeType implements BusinessErrorCode {
	FEEDBACK_ALREADY_ASKED(10401),
	FEEDBACK_ALREADY_CANCELLED(10402),
	FEEDBACK_ALREADY_RECEIVED(10403);

	private int value;

	FeedbackErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
