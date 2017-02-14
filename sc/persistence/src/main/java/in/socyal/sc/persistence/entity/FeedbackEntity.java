package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.socyal.sc.api.type.FeedbackStatusType;

@Entity
@Table(name = "FEEDBACK", schema = "Socyal")
public class FeedbackEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "MERCHANT_ID")
	private Integer merchantId;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private FeedbackStatusType status;
	
	@Column(name = "FOOD_RATING")
	private Double foodRating;
	
	@Column(name = "SERVICE_RATING")
	private Double serviceRating;
	
	@Column(name = "AMBIENCE_RATING")
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
