package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.response.GenericResponse;

public class ItemDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private DishDto dish;
	private List<RecommendationDto> reviews;
	private Integer totalRecommendations;
	
	public DishDto getDish() {
		return dish;
	}

	public void setDish(DishDto dish) {
		this.dish = dish;
	}

	public List<RecommendationDto> getReviews() {
		if (this.reviews == null) {
			this.reviews = new ArrayList<>();
		}
		return reviews;
	}

	public void setReviews(List<RecommendationDto> reviews) {
		this.reviews = reviews;
	}

	public Integer getTotalRecommendations() {
		return totalRecommendations;
	}

	public void setTotalRecommendations(Integer totalRecommendations) {
		this.totalRecommendations = totalRecommendations;
	}

	public Integer getReviewCount() {
		return this.getReviews().size();
	}
}
