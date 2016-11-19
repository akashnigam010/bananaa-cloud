package in.socyal.sc.api.restaurant.request;

import java.io.Serializable;

public class AllRestaurantsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String latitude;
	private String longitude;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
