package in.socyal.sc.api.feedback.request;

import java.io.Serializable;

public class SubmitFeedbackRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer checkinId;
	private Integer foodRating;
	private Integer serviceRating;
	private Integer ambienceRating;

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public Integer getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(Integer foodRating) {
		this.foodRating = foodRating;
	}

	public Integer getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(Integer serviceRating) {
		this.serviceRating = serviceRating;
	}

	public Integer getAmbienceRating() {
		return ambienceRating;
	}

	public void setAmbienceRating(Integer ambienceRating) {
		this.ambienceRating = ambienceRating;
	}
}
