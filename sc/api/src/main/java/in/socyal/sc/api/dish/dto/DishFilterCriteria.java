package in.socyal.sc.api.dish.dto;

public class DishFilterCriteria {
	private Boolean mapSuggestions;
	private Boolean mapCuisines;
	private Boolean mapRecommendations;
	private Boolean mapRecommendationsCount;
	private Boolean mapImages;

	public DishFilterCriteria(Boolean mapAllFilters) {
		this.mapSuggestions = mapAllFilters;
		this.mapCuisines = mapAllFilters;
		this.mapRecommendations = mapAllFilters;
		this.mapRecommendationsCount = mapAllFilters;
		this.mapImages = mapAllFilters;
	}

	public DishFilterCriteria(Boolean mapImages, Boolean mapRecommendationsCount) {
		this(Boolean.FALSE);
		this.mapRecommendationsCount = mapRecommendationsCount;
		this.mapImages = mapImages;
	}

	public DishFilterCriteria(Boolean mapSuggestions, Boolean mapCuisines, Boolean mapRecommendations,
			Boolean mapImages) {
		this.mapSuggestions = mapSuggestions;
		this.mapCuisines = mapCuisines;
		this.mapRecommendations = mapRecommendations;
		this.mapRecommendationsCount = Boolean.FALSE;
		this.mapImages = mapImages;
	}

	public Boolean getMapSuggestions() {
		return mapSuggestions;
	}

	public void setMapSuggestions(Boolean mapSuggestions) {
		this.mapSuggestions = mapSuggestions;
	}

	public Boolean getMapCuisines() {
		return mapCuisines;
	}

	public void setMapCuisines(Boolean mapCuisines) {
		this.mapCuisines = mapCuisines;
	}

	public Boolean getMapRecommendations() {
		return mapRecommendations;
	}

	public void setMapRecommendations(Boolean mapRecommendations) {
		this.mapRecommendations = mapRecommendations;
	}

	public Boolean getMapImages() {
		return mapImages;
	}

	public void setMapImages(Boolean mapImages) {
		this.mapImages = mapImages;
	}

	public Boolean getMapRecommendationsCount() {
		return mapRecommendationsCount;
	}

	public void setMapRecommendationsCount(Boolean mapRecommendationsCount) {
		this.mapRecommendationsCount = mapRecommendationsCount;
	}
}
