package in.socyal.sc.api.dish.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.api.type.RatingColorType;

public class DishDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String nameId;
	private String cost;
	private Float rating;
	private List<SuggestionDto> suggestions;
	private List<CuisineDto> cuisines;
	private MerchantDto merchant;
	private String imageUrl;
	private String thumbnail;
	private Boolean isActive;
	private String itemUrl;
	private String itemUrlAbsolute;
	private List<RecommendationDto> recommendations;
	private Integer recommendationCount;
	private String ratingClass;

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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public List<SuggestionDto> getSuggestions() {
		if (this.suggestions == null) {
			this.suggestions = new ArrayList<>();
		}
		return suggestions;
	}

	public void setSuggestions(List<SuggestionDto> suggestions) {
		this.suggestions = suggestions;
	}

	public List<CuisineDto> getCuisines() {
		if (this.cuisines == null) {
			this.cuisines = new ArrayList<>();
		}
		return cuisines;
	}

	public void setCuisines(List<CuisineDto> cuisines) {
		this.cuisines = cuisines;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getRatingClass() {
		if (rating != null) {
			if (rating == 0f) {
				ratingClass = RatingColorType.R25.getCssClass();
			} else {
				ratingClass = RatingColorType.getCodeByRating(rating).getCssClass();
			}
		}
		return ratingClass;
	}

	public void setRatingClass(String ratingClass) {
		this.ratingClass = ratingClass;
	}

	public Integer getRecommendationCount() {
		return recommendationCount;
	}

	public void setRecommendationCount(Integer recommendationCount) {
		this.recommendationCount = recommendationCount;
	}

	public String getItemUrlAbsolute() {
		return itemUrlAbsolute;
	}

	public void setItemUrlAbsolute(String itemUrlAbsolute) {
		this.itemUrlAbsolute = itemUrlAbsolute;
	}
}
