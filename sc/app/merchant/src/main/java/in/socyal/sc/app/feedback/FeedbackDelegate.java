package in.socyal.sc.app.feedback;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.response.RewardStatusResponse;

public interface FeedbackDelegate {
	RewardStatusResponse dismissFeedback(FeedbackRequest request) throws BusinessException;
	RewardStatusResponse submitFeedback(SubmitFeedbackRequest request) throws BusinessException;
	BusinessCheckinDetailsResponse businessAskFeedback(FeedbackRequest request) throws BusinessException;
	BusinessCheckinDetailsResponse businessCancelFeedback(FeedbackRequest request) throws BusinessException;
}
