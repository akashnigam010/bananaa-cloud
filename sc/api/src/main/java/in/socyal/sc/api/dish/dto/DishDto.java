package in.socyal.sc.api.dish.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;

public class DishDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String nameId;
	private Integer suggestionId;
	private Integer cuisineId;
	private MerchantDto merchant;
	private String imageUrl;
	private Boolean isActive;
	private Integer initialDump;
	private String itemUrl;
	private List<RecommendationDto> recommendations;

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

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public List<RecommendationDto> getRecommendations() {
		if (this.recommendations == null) {
			this.recommendations = new ArrayList<>();
		}
		return recommendations;
	}

	public void setRecommendations(List<RecommendationDto> recommendations) {
		this.recommendations = recommendations;
	}
}
