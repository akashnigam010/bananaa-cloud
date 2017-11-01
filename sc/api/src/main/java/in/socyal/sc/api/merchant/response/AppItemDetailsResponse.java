package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.type.RatingColorType;

public class AppItemDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer merchantId;
	private String name;
	private String merchantName;
	private String shortAddress;
	private String rating;
	private String imageUrl;
	private String itemUrl;
	private String itemUrlAbsolute;
	private String ratingClass;
	private Integer totalRatings;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
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

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRatingClass() {
		if (rating == "") {
			ratingClass = RatingColorType.R25.getCssClass();
		} else { 
			ratingClass = RatingColorType.getCodeByRating(Float.parseFloat(rating)).getCssClass();
		}
		return ratingClass;
	}

	public void setRatingClass(String ratingClass) {
		this.ratingClass = ratingClass;
	}

	public Integer getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(Integer totalRatings) {
		this.totalRatings = totalRatings;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getItemUrlAbsolute() {
		return itemUrlAbsolute;
	}

	public void setItemUrlAbsolute(String itemUrlAbsolute) {
		this.itemUrlAbsolute = itemUrlAbsolute;
	}
}
