package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.persistence.entity.FeedbackEntity;

@Component
public class FeedbackDaoMapper {

	public FeedbackDto mapToFeedbackDto(FeedbackEntity from) {
		FeedbackDto to = new FeedbackDto();
		to.setId(from.getId());
		to.setMerchantId(from.getMerchantId());
		to.setStatus(from.getStatus());
		to.setUserId(from.getUserId());
		to.setAmbienceRating(from.getAmbienceRating());
		to.setFoodRating(from.getFoodRating());
		to.setServiceRating(from.getServiceRating());
		return to;
	}
}
