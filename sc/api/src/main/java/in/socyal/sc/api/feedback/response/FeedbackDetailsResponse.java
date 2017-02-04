package in.socyal.sc.api.feedback.response;

import java.io.Serializable;

public class FeedbackDetailsResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String foodRating;
	private String ambienceRating;
	private String serviceRating;

	public String getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(String foodRating) {
		this.foodRating = foodRating;
	}

	public String getAmbienceRating() {
		return ambienceRating;
	}

	public void setAmbienceRating(String ambienceRating) {
		this.ambienceRating = ambienceRating;
	}

	public String getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(String serviceRating) {
		this.serviceRating = serviceRating;
	}
}
