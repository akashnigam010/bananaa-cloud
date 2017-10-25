package in.socyal.sc.api.items.request;

public class GetFoodSuggestionsRequest {
	private Integer locationId;
	private Boolean isCity;
	private Integer page;

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Boolean getIsCity() {
		return isCity;
	}

	public void setIsCity(Boolean isCity) {
		this.isCity = isCity;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
