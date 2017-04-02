package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class ItemDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String merchantName;
	private String merchantShortAddress;
	private String merchantUrl;
	private Integer recommendations;
	private String imageUrl;
	private List<Review> reviews;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantShortAddress() {
		return merchantShortAddress;
	}

	public void setMerchantShortAddress(String merchantShortAddress) {
		this.merchantShortAddress = merchantShortAddress;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public Integer getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Integer recommendations) {
		this.recommendations = recommendations;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Review> getReviews() {
		if (this.reviews == null) {
			this.reviews = new ArrayList<>();
		}
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
}
