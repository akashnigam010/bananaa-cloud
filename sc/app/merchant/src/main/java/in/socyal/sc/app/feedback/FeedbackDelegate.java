package in.socyal.sc.app.feedback;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.helper.exception.BusinessException;

public interface FeedbackDelegate {
	BusinessCheckinDetailsResponse businessAskFeedback(FeedbackRequest request) throws BusinessException;
	BusinessCheckinDetailsResponse businessCancelFeedback(FeedbackRequest request) throws BusinessException;
}
