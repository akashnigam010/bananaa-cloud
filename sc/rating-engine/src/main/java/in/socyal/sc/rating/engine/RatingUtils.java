package in.socyal.sc.rating.engine;

import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.RecommendationEntity;

@Component
public class RatingUtils {
	private static final Integer MAX_DISH_TRIAL_COUNT = 10;
	private static final Float MAX_DISH_TRIAL_RATING = 5.0f;

	public static Float getTriedValue(DishEntity entity) {
		if (entity.getRecommendations().size() > MAX_DISH_TRIAL_COUNT) {
			return MAX_DISH_TRIAL_RATING;
		} else {
			return entity.getRecommendations().size() * (MAX_DISH_TRIAL_RATING / MAX_DISH_TRIAL_COUNT);
		}
	}

	public static Float getUserRatedValue(DishEntity entity) {
		Float credibilityTotal = 0.0f;
		Float ratingTotal = 0.0f;
		for (RecommendationEntity rateOb : entity.getRecommendations()) {
			if (rateOb.getIsActive()) {
				credibilityTotal += rateOb.getUser().getCredibility();
				ratingTotal += rateOb.getRating() * rateOb.getUser().getCredibility();
			}			
		}
		if (credibilityTotal > 0) {
			return ratingTotal / credibilityTotal;
		} else {
			return 0.0f;
		}
	}

}
