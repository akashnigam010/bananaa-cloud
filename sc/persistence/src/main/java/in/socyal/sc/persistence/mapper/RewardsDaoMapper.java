package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.rewards.dto.RewardsDto;
import in.socyal.sc.persistence.entity.RewardsEntity;

@Component
public class RewardsDaoMapper {
	
	public void map(RewardsEntity from, RewardsDto to) {
		to.setId(from.getId());
		to.setMerchantId(from.getMerchantId());
		to.setReward(from.getReward());
		to.setStatus(from.getStatus());
		to.setValue(from.getValue());
	}
	
	public void map(List<RewardsEntity> from, List<RewardsDto> to) {
		for (RewardsEntity entity : from) {
			RewardsDto dto = new RewardsDto();
			map(entity, dto);
			to.add(dto);
		}
	}
}
