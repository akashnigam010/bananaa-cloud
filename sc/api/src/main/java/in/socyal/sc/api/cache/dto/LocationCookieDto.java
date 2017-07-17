package in.socyal.sc.api.cache.dto;

public class LocationCookieDto {
	private boolean isCitySearch;
	private String localityId;
	private String cityId;
	private String locationName;

	public LocationCookieDto(boolean isCitySearch, String cityId, String localityId, String locationName) {
		this.isCitySearch = isCitySearch;
		this.cityId = cityId;
		this.localityId = localityId;
		this.locationName = locationName;
	}

	public boolean isCitySearch() {
		return isCitySearch;
	}

	public String getLocalityId() {
		return localityId;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getCityId() {
		return cityId;
	}
}
