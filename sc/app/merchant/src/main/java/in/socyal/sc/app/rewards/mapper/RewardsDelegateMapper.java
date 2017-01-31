package in.socyal.sc.app.rewards.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.reward.business.response.RewardsResponse;
import in.socyal.sc.api.rewards.dto.RewardsDto;

@Component
public class RewardsDelegateMapper {

	public void map(List<RewardsDto> from, GetBusinessRewardsResponse to) {
		List<RewardsResponse> response = new ArrayList<>();
		for (RewardsDto reward : from) {
			RewardsResponse rewardResponse = new RewardsResponse();
			map(reward, rewardResponse);
			response.add(rewardResponse);
		}
		to.setRewards(response);
	}
	
	public void map(RewardsDto from, RewardsResponse to) {
		to.setId(from.getId());
		to.setMerchantId(from.getMerchantId());
		to.setReward(from.getReward());
	}
}
