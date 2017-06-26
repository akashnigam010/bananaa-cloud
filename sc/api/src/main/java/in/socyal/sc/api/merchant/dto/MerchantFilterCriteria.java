package in.socyal.sc.api.merchant.dto;

public class MerchantFilterCriteria {
	private Boolean mapImage;
	private Boolean mapAddress;
	private Boolean mapTimings;
	private Boolean mapCuisineRatings;
	private Boolean mapSuggestionRatings;
	private Boolean mapOtherDetails;

	public MerchantFilterCriteria(Boolean value) {
		this.mapImage = value;
		this.mapAddress = value;
		this.mapTimings = value;
		this.mapCuisineRatings = value;
		this.mapSuggestionRatings = value;
		this.mapOtherDetails = value;
	}

	public MerchantFilterCriteria(Boolean mapImage, Boolean mapAddress) {
		this(Boolean.FALSE);
		this.mapImage = mapImage;
		this.mapAddress = mapAddress;
	}

	public MerchantFilterCriteria(Boolean mapImage, Boolean mapAddress, Boolean mapTimings, Boolean mapCuisineRatings,
			Boolean mapSuggestionRatings, Boolean mapOtherDetails) {
		this.mapImage = mapImage;
		this.mapAddress = mapAddress;
		this.mapTimings = mapTimings;
		this.mapCuisineRatings = mapCuisineRatings;
		this.mapSuggestionRatings = mapSuggestionRatings;
		this.mapOtherDetails = mapOtherDetails;
	}

	public Boolean getMapImage() {
		return mapImage;
	}

	public void setMapImage(Boolean mapImage) {
		this.mapImage = mapImage;
	}

	public Boolean getMapAddress() {
		return mapAddress;
	}

	public void setMapAddress(Boolean mapAddress) {
		this.mapAddress = mapAddress;
	}

	public Boolean getMapTimings() {
		return mapTimings;
	}

	public void setMapTimings(Boolean mapTimings) {
		this.mapTimings = mapTimings;
	}

	public Boolean getMapOtherDetails() {
		return mapOtherDetails;
	}

	public void setMapOtherDetails(Boolean mapOtherDetails) {
		this.mapOtherDetails = mapOtherDetails;
	}

	public Boolean getMapCuisineRatings() {
		return mapCuisineRatings;
	}

	public void setMapCuisineRatings(Boolean mapCuisineRatings) {
		this.mapCuisineRatings = mapCuisineRatings;
	}

	public Boolean getMapSuggestionRatings() {
		return mapSuggestionRatings;
	}

	public void setMapSuggestionRatings(Boolean mapSuggestionRatings) {
		this.mapSuggestionRatings = mapSuggestionRatings;
	}
}
