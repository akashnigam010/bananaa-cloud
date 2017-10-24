package in.socyal.sc.api.manage.request;

import java.util.List;

public class NewMerchantRequest {
	private String name;
	private String nameId;
	private String phone;
	private String address;
	private Integer localityId;
	private List<String> type;
	private Float averageCost;
	private String thumbnail;
	private String imageUrl;
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getLocalityId() {
		return localityId;
	}
	public void setLocalityId(Integer localityId) {
		this.localityId = localityId;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public Float getAverageCost() {
		return averageCost;
	}
	public void setAverageCost(Float averageCost) {
		this.averageCost = averageCost;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
