package in.socyal.sc.persistence.entity;

public class DishCount {
	private Long count;
	private Integer cuisineId;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(Integer cuisineId) {
		this.cuisineId = cuisineId;
	}
}
