package in.socyal.sc.core.validation;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class FeedbackValidator {
	public void validateFeedbackRequest(FeedbackRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateSubmitFeedbackRequest(SubmitFeedbackRequest request) {
		if (request.getCheckinId() == null || request.getFoodRating() == null || request.getServiceRating() == null
				|| request.getAmbienceRating() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
