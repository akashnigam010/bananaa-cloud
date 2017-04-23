package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LOCALITY", schema = "bna")
public class LocalityEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CITY")
	private String city;

	@Column(name = "LATITUDE")
	private Double latitude;

	@Column(name = "LONGITUDE")
	private Double longitude;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
