package in.socyal.sc.core.validation;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.reward.request.RewardRequest;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class RewardsValidator {
	public void validateSubmitRewardsRequest(SubmitRewardsRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateRewardRequest(RewardRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
