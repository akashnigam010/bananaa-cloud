package in.socyal.sc.api.dish.dto;

public class DishFilterCriteria {
	private Boolean mapSuggestions;
	private Boolean mapCuisines;
	private Boolean mapRecommendations;
	private Boolean mapImages;

	public DishFilterCriteria(Boolean mapAllFilters) {
		this.mapSuggestions = mapAllFilters;
		this.mapCuisines = mapAllFilters;
		this.mapRecommendations = mapAllFilters;
		this.setMapImages(mapAllFilters);
	}

	public DishFilterCriteria(Boolean mapSuggestions, Boolean mapCuisines) {
		this.mapSuggestions = mapSuggestions;
		this.mapCuisines = mapCuisines;
		this.mapRecommendations = Boolean.FALSE;
		this.setMapImages(Boolean.FALSE);
	}

	public DishFilterCriteria(Boolean mapSuggestions, Boolean mapCuisines, Boolean mapRecommendations,
			Boolean mapImages) {
		this.mapSuggestions = mapSuggestions;
		this.mapCuisines = mapCuisines;
		this.mapRecommendations = mapRecommendations;
		this.setMapImages(mapImages);
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
}
