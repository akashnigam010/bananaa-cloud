package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.error.GenericErrorCodeType;

@Component
public class FeedbackValidator {
	public void validateFeedbackRequest(FeedbackRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateSubmitFeedbackRequest(SubmitFeedbackRequest request) {
		if (request.getCheckinId() == null || StringUtils.isEmpty(request.getFoodRating())
				|| StringUtils.isEmpty(request.getServiceRating())
				|| StringUtils.isEmpty(request.getAmbienceRating())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
