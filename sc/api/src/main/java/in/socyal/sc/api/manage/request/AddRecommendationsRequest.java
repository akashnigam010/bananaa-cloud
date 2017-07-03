package in.socyal.sc.api.manage.request;

public class AddRecommendationsRequest {
	private Integer itemId;
	private Float rating;
	private Integer rcmdCount;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getRcmdCount() {
		return rcmdCount;
	}

	public void setRcmdCount(Integer rcmdCount) {
		this.rcmdCount = rcmdCount;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
}
