package in.socyal.sc.app.response;

import java.util.List;

import in.socyal.sc.api.item.response.Item;
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.response.GenericResponse;

public class MerchantDetailsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String nameId;
	private String shortAddress;
	private String imageUrl;
	private String thumbnail;
	private String merchantUrl;
	private String merchantUrlAbsolute;
	private String phone;
	private List<String> openingHours;
	private List<String> type;
	private String averageCost;
	private String longAddress;
	private List<Tag> ratedCuisines;
	private List<Item> items;

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

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(List<String> openingHours) {
		this.openingHours = openingHours;
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

	public List<Tag> getRatedCuisines() {
		return ratedCuisines;
	}

	public void setRatedCuisines(List<Tag> ratedCuisines) {
		this.ratedCuisines = ratedCuisines;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getMerchantUrlAbsolute() {
		return merchantUrlAbsolute;
	}

	public void setMerchantUrlAbsolute(String merchantUrlAbsolute) {
		this.merchantUrlAbsolute = merchantUrlAbsolute;
	}
}
