package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

public class LocationResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Double latitude;
	private Double longitude;

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
