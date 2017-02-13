package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.feedback.response.FeedbackStatusResponse;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.reward.request.RewardRequest;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;
import in.socyal.sc.app.rewards.RewardsDelegate;
import in.socyal.sc.core.validation.RewardsValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.JwtTokenDetailsHelper;

@RestController
@RequestMapping(value = "/socyal/reward")
public class RewardService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	RewardsValidator validator;
	@Autowired
	RewardsDelegate delegate;
	@Autowired
	JwtTokenDetailsHelper jwtDetailsHelper;

	@RequestMapping(value = "/submitRewards", method = RequestMethod.POST, headers = "Accept=application/json")
	public BusinessCheckinDetailsResponse submitRewards(@RequestBody SubmitRewardsRequest request) {
		JsonHelper.logRequest(request, RewardService.class, "/reward/submitRewards");
		BusinessCheckinDetailsResponse response = new BusinessCheckinDetailsResponse();
		try {
			validator.validateSubmitRewardsRequest(request);
			response = delegate.submitRewards(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	/**
	 * Response of dismiss reward service returns the status of the feedback
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dismissReward", method = RequestMethod.POST, headers = "Accept=application/json")
	public FeedbackStatusResponse dismissReward(@RequestBody RewardRequest request) {
		JsonHelper.logRequest(request, RewardService.class, "/reward/dismissReward");
		FeedbackStatusResponse response = new FeedbackStatusResponse();
		try {
			validator.validateRewardRequest(request);
			response = delegate.dismissReward(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getBusinessRewards", method = RequestMethod.GET, headers = "Accept=application/json")
	public GetBusinessRewardsResponse getBusinessRewards() {
		GetBusinessRewardsResponse response = new GetBusinessRewardsResponse();
		try {
			response = delegate.getRewardsList(jwtDetailsHelper.getCurrentMerchantId());
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
