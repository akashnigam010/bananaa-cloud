package in.socyal.sc.api.feedback.dto;

import java.io.Serializable;

import in.socyal.sc.api.type.FeedbackStatusType;

public class FeedbackDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer checkinId;
	private Integer userId;
	private Integer merchantId;
	private FeedbackStatusType status;
	private Integer foodRating;
	private Integer serviceRating;
	private Integer ambienceRating;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public FeedbackStatusType getStatus() {
		return status;
	}

	public void setStatus(FeedbackStatusType status) {
		this.status = status;
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
