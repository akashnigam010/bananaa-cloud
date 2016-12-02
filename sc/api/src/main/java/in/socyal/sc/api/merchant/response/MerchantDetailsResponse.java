package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class MerchantDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String shortAddress;
	private Integer checkins;
	private String imageUrl;
	private Double rating;
	private Boolean isOpen;
	private Double distance;
	private String openTime;
	private List<String> cuisines;
	private List<String> type;
	private Double averageCost;
	private String longAddress;
	private LocationResponse location;

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

	public List<String> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<String> cuisines) {
		this.cuisines = cuisines;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	public String getLongAddress() {
		return longAddress;
	}

	public void setLongAddress(String longAddress) {
		this.longAddress = longAddress;
	}

	public LocationResponse getLocation() {
		return location;
	}

	public void setLocation(LocationResponse location) {
		this.location = location;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
}