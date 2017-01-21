package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.api.reward.response.RewardStatusResponse;
import in.socyal.sc.core.validation.FeedbackValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;

@RestController
@RequestMapping(value = "/feedback")
public class FeedbackService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	FeedbackValidator validator;

	// FIXME : Add new service - Ask for Feedback

	// FIXME : Add new service - Cancel Feedback

	/**
	 * Response of dismiss feedback service returns the status of the reward message
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dismissFeedback", method = RequestMethod.POST, headers = "Accept=application/json")
	public RewardStatusResponse dismissFeedback(@RequestBody FeedbackRequest request) {
		JsonHelper.logRequest(request, FeedbackService.class, "/feedback/dismissFeedback");
		RewardStatusResponse response = new RewardStatusResponse();
		try {
			validator.validateFeedbackRequest(request);
			// FIXME : Add actual logic to set DISMISS status of feedback for a checkin
			// FIXME : Add actual logic to fetch reward status for a checkin
			response.setCheckinId(request.getCheckinId());
			if (request.getCheckinId() % 2 == 0) {
				response.setShowReward(Boolean.FALSE);
			} else {
				response.setShowReward(Boolean.TRUE);
				response.setRewardMessage(
						"Won an exciting gift card worth Rs. 100 and additional discount worth Rs. 250");
			}
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	/**
	 * Response of submit feedback service returns the status of the reward message
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitFeedback", method = RequestMethod.POST, headers = "Accept=application/json")
	public RewardStatusResponse submitFeedback(@RequestBody SubmitFeedbackRequest request) {
		JsonHelper.logRequest(request, FeedbackService.class, "/feedback/submitFeedback");
		RewardStatusResponse response = new RewardStatusResponse();
		try {
			validator.validateSubmitFeedbackRequest(request);
			// FIXME : Add actual logic to set SUBMITTED status of feedback for a checkin
			// FIXME : Add actual logic to fetch reward status for a checkin
			response.setCheckinId(request.getCheckinId());
			if (request.getCheckinId() % 2 == 0) {
				response.setShowReward(Boolean.FALSE);
			} else {
				response.setShowReward(Boolean.TRUE);
				response.setRewardMessage(
						"Won an exciting gift card worth Rs. 100 and additional discount worth Rs. 250");
			}
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
