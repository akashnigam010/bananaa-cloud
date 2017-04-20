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
	private List<RecommendationDto> recommendations;
	private Integer totalRecommendations;

	public DishDto getDish() {
		return dish;
	}

	public void setDish(DishDto dish) {
		this.dish = dish;
	}

	public List<RecommendationDto> getRecommendations() {
		if (this.recommendations == null) {
			this.recommendations = new ArrayList<>();
		}
		return recommendations;
	}

	public void setRecommendations(List<RecommendationDto> recommendations) {
		this.recommendations = recommendations;
	}

	public Integer getTotalRecommendations() {
		return totalRecommendations;
	}

	public void setTotalRecommendations(Integer totalRecommendations) {
		this.totalRecommendations = totalRecommendations;
	}
}
