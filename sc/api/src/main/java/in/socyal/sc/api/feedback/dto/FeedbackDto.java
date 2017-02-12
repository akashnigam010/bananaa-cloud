package in.socyal.sc.api.feedback.dto;

import java.io.Serializable;

import in.socyal.sc.api.type.FeedbackStatusType;

public class FeedbackDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer merchantId;
	private FeedbackStatusType status;
	private Double foodRating;
	private Double serviceRating;
	private Double ambienceRating;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(Double foodRating) {
		this.foodRating = foodRating;
	}

	public Double getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(Double serviceRating) {
		this.serviceRating = serviceRating;
	}

	public Double getAmbienceRating() {
		return ambienceRating;
	}

	public void setAmbienceRating(Double ambienceRating) {
		this.ambienceRating = ambienceRating;
	}
}
