package in.socyal.sc.api.dish.dto;

import java.io.Serializable;

import in.socyal.sc.api.merchant.dto.MerchantDto;

public class DishDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer suggestionId;
	private Integer cuisineId;
	private MerchantDto merchant;
	private String imageUrl;
	private Boolean isActive;
	private Integer initialDump;

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

	public Integer getSuggestionId() {
		return suggestionId;
	}

	public void setSuggestionId(Integer suggestionId) {
		this.suggestionId = suggestionId;
	}

	public Integer getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(Integer cuisineId) {
		this.cuisineId = cuisineId;
	}

	public MerchantDto getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDto merchant) {
		this.merchant = merchant;
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

	public Integer getInitialDump() {
		return initialDump;
	}

	public void setInitialDump(Integer initialDump) {
		this.initialDump = initialDump;
	}
}
