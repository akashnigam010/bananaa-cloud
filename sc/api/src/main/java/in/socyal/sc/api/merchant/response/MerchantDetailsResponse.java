package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class MerchantDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nameId;
	private String name;
	private String shortAddress;
	private String imageUrl;
	private List<String> openingHours;
	private List<String> type;
	private String averageCost;
	private String longAddress;
	private String phone;
	private Integer recommendations;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public String getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(String averageCost) {
		this.averageCost = averageCost;
	}

	public String getLongAddress() {
		return longAddress;
	}

	public void setLongAddress(String longAddress) {
		this.longAddress = longAddress;
	}

	public List<String> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(List<String> openingHours) {
		this.openingHours = openingHours;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public Integer getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Integer recommendations) {
		this.recommendations = recommendations;
	}
}
