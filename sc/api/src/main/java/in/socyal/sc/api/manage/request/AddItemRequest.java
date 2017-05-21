package in.socyal.sc.api.manage.request;

import java.util.List;

public class AddItemRequest {
	private String name;
	private Integer merchantId;
	private List<Integer> cuisineIds;
	private List<Integer> suggestionIds;
	private String imageUrl;
	private String thumbnail;
	private Boolean isActive;
	private String nameId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public List<Integer> getCuisineIds() {
		return cuisineIds;
	}

	public void setCuisineIds(List<Integer> cuisineIds) {
		this.cuisineIds = cuisineIds;
	}

	public List<Integer> getSuggestionIds() {
		return suggestionIds;
	}

	public void setSuggestionIds(List<Integer> suggestionIds) {
		this.suggestionIds = suggestionIds;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
