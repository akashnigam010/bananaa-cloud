package in.socyal.sc.api.location.dto;

import java.io.Serializable;

public class LocalityDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String nameId;
	private CityDto city;
	private Double latitude;
	private Double longitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getShortAddress() {
		if (this.city != null) {
			return this.name + ", " + this.city.getName();
		}
		return null;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public CityDto getCity() {
		return city;
	}

	public void setCity(CityDto city) {
		this.city = city;
	}
}
