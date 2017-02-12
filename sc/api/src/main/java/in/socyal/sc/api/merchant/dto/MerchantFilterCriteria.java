package in.socyal.sc.api.merchant.dto;

public class MerchantFilterCriteria {
	private Boolean mapImage;
	private Boolean mapAddress;
	private Boolean mapTimings;
	private Boolean mapRating;
	private Boolean mapCheckins;
	private Boolean mapOtherDetails;

	public MerchantFilterCriteria(Boolean mapAllFilters) {
		this.mapImage = mapAllFilters;
		this.mapAddress = mapAllFilters;
		this.mapTimings = mapAllFilters;
		this.mapRating = mapAllFilters;
		this.mapCheckins = mapAllFilters;
		this.mapOtherDetails = mapAllFilters;
	}

	public MerchantFilterCriteria(Boolean mapImage, Boolean mapAddress) {
		this.mapImage = mapImage;
		this.mapAddress = mapAddress;
		this.mapTimings = Boolean.FALSE;
		this.mapRating = Boolean.FALSE;
		this.mapCheckins = Boolean.FALSE;
		this.mapOtherDetails = Boolean.FALSE;
	}

	public MerchantFilterCriteria(Boolean mapImage, Boolean mapAddress, Boolean mapTimings, Boolean mapRating,
			Boolean mapCheckins, Boolean mapOtherDetails) {
		this.mapImage = mapImage;
		this.mapAddress = mapAddress;
		this.mapTimings = mapTimings;
		this.mapRating = mapRating;
		this.mapCheckins = mapCheckins;
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

	public Boolean getMapRating() {
		return mapRating;
	}

	public void setMapRating(Boolean mapRating) {
		this.mapRating = mapRating;
	}

	public Boolean getMapOtherDetails() {
		return mapOtherDetails;
	}

	public void setMapOtherDetails(Boolean mapOtherDetails) {
		this.mapOtherDetails = mapOtherDetails;
	}

	public Boolean getMapCheckins() {
		return mapCheckins;
	}

	public void setMapCheckins(Boolean mapCheckins) {
		this.mapCheckins = mapCheckins;
	}
}
