package in.socyal.sc.app.rewards;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;
import in.socyal.sc.helper.exception.BusinessException;

public interface RewardsDelegate {
	GetBusinessRewardsResponse getRewardsList(Integer merchantId) throws BusinessException;
	BusinessCheckinDetailsResponse submitRewards(SubmitRewardsRequest request) throws BusinessException;
}
