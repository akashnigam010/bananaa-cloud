package in.socyal.sc.app.rewards;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.feedback.response.FeedbackStatusResponse;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.reward.request.RewardRequest;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;

public interface RewardsDelegate {
	GetBusinessRewardsResponse getRewardsList(Integer merchantId) throws BusinessException;

	BusinessCheckinDetailsResponse submitRewards(SubmitRewardsRequest request) throws BusinessException;

	FeedbackStatusResponse dismissReward(RewardRequest request) throws BusinessException;
}
