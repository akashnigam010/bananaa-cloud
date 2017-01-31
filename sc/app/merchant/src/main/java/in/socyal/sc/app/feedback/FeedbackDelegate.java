package in.socyal.sc.app.feedback;

import in.socyal.sc.api.feedback.business.request.BusinessAskFeedbackRequest;
import in.socyal.sc.api.feedback.business.request.BusinessCancelFeedbackRequest;
import in.socyal.sc.api.feedback.business.response.BusinessAskFeedbackResponse;
import in.socyal.sc.api.feedback.business.response.BusinessCancelFeedbackResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface FeedbackDelegate {
	BusinessAskFeedbackResponse businessAskFeedback(BusinessAskFeedbackRequest request) throws BusinessException;
	BusinessCancelFeedbackResponse businessCancelFeedback(BusinessCancelFeedbackRequest request) throws BusinessException;
}
