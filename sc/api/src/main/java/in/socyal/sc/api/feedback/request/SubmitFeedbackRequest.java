package in.socyal.sc.api.feedback.request;

import java.io.Serializable;

public class SubmitFeedbackRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private String foodRating;
	private String serviceRating;
	private String ambienceRating;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public String getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(String foodRating) {
		this.foodRating = foodRating;
	}

	public String getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(String serviceRating) {
		this.serviceRating = serviceRating;
	}

	public String getAmbienceRating() {
		return ambienceRating;
	}

	public void setAmbienceRating(String ambienceRating) {
		this.ambienceRating = ambienceRating;
	}
}
