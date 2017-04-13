package in.socyal.sc.api.items.dto;

import in.socyal.sc.api.dish.dto.DishDto;

public class DishDetailsResultDto {
	private Long recommendations;
	private DishDto dish;

	public Long getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Long recommendations) {
		this.recommendations = recommendations;
	}

	public DishDto getDish() {
		return dish;
	}

	public void setDish(DishDto dish) {
		this.dish = dish;
	}
}
