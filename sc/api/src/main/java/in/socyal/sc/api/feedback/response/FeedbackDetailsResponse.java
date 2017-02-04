package in.socyal.sc.api.feedback.response;

import java.io.Serializable;

public class FeedbackDetailsResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer foodRating;
	private Integer ambienceRating;
	private Integer serviceRating;

	public Integer getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(Integer foodRating) {
		this.foodRating = foodRating;
	}

	public Integer getAmbienceRating() {
		return ambienceRating;
	}

	public void setAmbienceRating(Integer ambienceRating) {
		this.ambienceRating = ambienceRating;
	}

	public Integer getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(Integer serviceRating) {
		this.serviceRating = serviceRating;
	}
}
