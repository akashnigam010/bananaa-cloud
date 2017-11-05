package in.socyal.sc.api.cache.dto;

public class LocationCookieDto {
	private boolean isCitySearch;
	private String localityId;
	private String cityId;
	private String locationName;
	
	// to be used by app services only
	private Integer id;
	private Boolean isCity;
	private String searchString;
	private boolean isSearchById;;
	
	/**
	 * Constructor for app services only
	 * @param id
	 * @param isCity
	 */
	public LocationCookieDto(Integer id, Boolean isCity, String searchString) {
		this.id = id;
		this.isCity = isCity;
		this.isSearchById = true;
		this.searchString = searchString;
	}

	/**
	 * Constructor for web services only
	 * @param isCitySearch
	 * @param cityId
	 * @param localityId
	 * @param locationName
	 */
	public LocationCookieDto(boolean isCitySearch, String cityId, String localityId, String locationName) {
		this.isCitySearch = isCitySearch;
		this.cityId = cityId;
		this.localityId = localityId;
		this.locationName = locationName;
		this.isSearchById = false;
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
	
	public Integer getId() {
		return id;
	}
	
	public Boolean getIsCity() {
		return isCity;
	}
	
	public boolean isSearchById() {
		return isSearchById;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
