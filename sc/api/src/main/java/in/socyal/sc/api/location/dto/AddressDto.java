package in.socyal.sc.api.location.dto;

import java.io.Serializable;

public class AddressDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String address;
	private LocalityDto locality;
	private Double latitude;
	private Double longitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalityDto getLocality() {
		return locality;
	}

	public void setLocality(LocalityDto locality) {
		this.locality = locality;
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
