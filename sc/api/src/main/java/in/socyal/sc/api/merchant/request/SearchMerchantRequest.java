package in.socyal.sc.api.merchant.request;

import java.io.Serializable;

public class SearchMerchantRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String searchString;
	private Double latitude;
	private Double longitude;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
