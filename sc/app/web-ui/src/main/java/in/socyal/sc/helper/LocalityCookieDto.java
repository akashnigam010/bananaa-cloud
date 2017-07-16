package in.socyal.sc.helper;

public class LocalityCookieDto {
	private boolean isCitySearch;
	private String localityId;
	private String locationName;

	public LocalityCookieDto(boolean isCitySearch, String localityId, String locationName) {
		this.isCitySearch = isCitySearch;
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
}
