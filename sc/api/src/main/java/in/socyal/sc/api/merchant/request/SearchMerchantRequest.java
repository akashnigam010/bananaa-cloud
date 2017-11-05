package in.socyal.sc.api.merchant.request;

import in.socyal.sc.api.type.TagType;

public class SearchMerchantRequest {
	private String nameId;
	private TagType type;
	private Integer page;
	private String localityNameId;
	private String cityNameId;
	
	// additionally to be used for mobile services only
	private Integer tagId;
	private Integer locationId;
	private Boolean isCity;
	private Boolean isTagSearch;
	private Boolean isMerchantSearch;
	private String searchString;

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public TagType getType() {
		return type;
	}

	public void setType(TagType type) {
		this.type = type;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getLocalityNameId() {
		return localityNameId;
	}

	public void setLocalityNameId(String localityNameId) {
		this.localityNameId = localityNameId;
	}

	public String getCityNameId() {
		return cityNameId;
	}

	public void setCityNameId(String cityNameId) {
		this.cityNameId = cityNameId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer id) {
		this.tagId = id;
	}

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

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Boolean getIsTagSearch() {
		return isTagSearch;
	}

	public void setIsTagSearch(Boolean isTagSearch) {
		this.isTagSearch = isTagSearch;
	}

	public Boolean getIsMerchantSearch() {
		return isMerchantSearch;
	}

	public void setIsMerchantSearch(Boolean isMerchantSearch) {
		this.isMerchantSearch = isMerchantSearch;
	}
}
