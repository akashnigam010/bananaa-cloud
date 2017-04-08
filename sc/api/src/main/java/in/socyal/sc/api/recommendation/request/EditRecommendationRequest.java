package in.socyal.sc.api.recommendation.request;

public class EditRecommendationRequest {
	private Integer rcmdnId;
	private Integer dishId;
	private String description;

	public Integer getRcmdnId() {
		return rcmdnId;
	}

	public void setRcmdnId(Integer rcmdnId) {
		this.rcmdnId = rcmdnId;
	}

	public Integer getDishId() {
		return dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
