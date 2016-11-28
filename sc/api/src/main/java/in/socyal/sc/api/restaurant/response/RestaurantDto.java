package in.socyal.sc.api.restaurant.response;

import java.io.Serializable;

public class RestaurantDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String shortAddress;
	private Integer checkins;
	private String imageUrl;
	private Double rating;
	private Boolean isOpen;
	private Double distance;

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

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public Integer getCheckins() {
		return checkins;
	}

	public void setCheckins(Integer checkins) {
		this.checkins = checkins;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
