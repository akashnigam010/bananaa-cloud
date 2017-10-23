package in.socyal.sc.api.recommendation.request;

public class RatingRequest {
	private Integer id;
	private Float rating;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
}
