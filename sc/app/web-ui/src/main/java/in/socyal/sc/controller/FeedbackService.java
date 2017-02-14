package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.response.RewardStatusResponse;
import in.socyal.sc.app.feedback.FeedbackDelegate;
import in.socyal.sc.core.validation.FeedbackValidator;
import in.socyal.sc.helper.JsonHelper;

@RestController
@RequestMapping(value = "/socyal/feedback")
public class FeedbackService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	FeedbackValidator validator;
	@Autowired
	FeedbackDelegate delegate;

	/**
	 * Response of dismiss feedback service returns the status of the reward
	 * message
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dismissFeedback", method = RequestMethod.POST, headers = "Accept=application/json")
	public RewardStatusResponse dismissFeedback(@RequestBody FeedbackRequest request) {
		JsonHelper.logRequest(request, FeedbackService.class, "/feedback/dismissFeedback");
		RewardStatusResponse response = new RewardStatusResponse();
		try {
			validator.validateFeedbackRequest(request);
			response = delegate.dismissFeedback(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	/**
	 * Response of submit feedback service returns the status of the reward
	 * message
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitFeedback", method = RequestMethod.POST, headers = "Accept=application/json")
	public RewardStatusResponse submitFeedback(@RequestBody SubmitFeedbackRequest request) {
		JsonHelper.logRequest(request, FeedbackService.class, "/feedback/submitFeedback");
		RewardStatusResponse response = new RewardStatusResponse();
		try {
			validator.validateSubmitFeedbackRequest(request);
			response = delegate.submitFeedback(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/businessAskFeedback", method = RequestMethod.POST, headers = "Accept=application/json")
	public BusinessCheckinDetailsResponse businessAskFeedback(@RequestBody FeedbackRequest request) {
		JsonHelper.logRequest(request, CheckinService.class, "/feedback/businessAskFeedback");
		BusinessCheckinDetailsResponse response = new BusinessCheckinDetailsResponse();
		try {
			validator.validateFeedbackRequest(request);
			response = delegate.businessAskFeedback(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/businessCancelFeedback", method = RequestMethod.POST, headers = "Accept=application/json")
	public BusinessCheckinDetailsResponse businessCancelFeedback(@RequestBody FeedbackRequest request) {
		JsonHelper.logRequest(request, CheckinService.class, "/feedback/businessCancelFeedback");
		BusinessCheckinDetailsResponse response = new BusinessCheckinDetailsResponse();
		try {
			validator.validateFeedbackRequest(request);
			response = delegate.businessCancelFeedback(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
