package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.persistence.entity.FeedbackEntity;

@Component
public class FeedbackDaoMapper {

	public void map(FeedbackDto from, FeedbackEntity to) {
		to.setCheckinId(from.getCheckinId());
		to.setMerchantId(from.getMerchantId());
		to.setStatus(from.getStatus());
		to.setUserId(from.getUserId());
		to.setAmbienceRating(from.getAmbienceRating());
		to.setFoodRating(from.getFoodRating());
		to.setServiceRating(from.getServiceRating());
	}
}
