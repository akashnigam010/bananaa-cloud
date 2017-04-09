package in.socyal.sc.persistence.entity;

public class PopularDishesResult {
	private Long recommendations;
	private DishEntity dish;

	public Long getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Long recommendations) {
		this.recommendations = recommendations;
	}

	public DishEntity getDish() {
		return dish;
	}

	public void setDish(DishEntity dish) {
		this.dish = dish;
	}
}
