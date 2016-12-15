package in.socyal.sc.api.merchant.request;

import java.io.Serializable;

public class SaveMerchantDetailsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String merchantName;
	private String imageUrl;
	private Double openTime;
	private Double closeTime;
	private Double rating;
	private String cuisines;
	private String telephone;
	private String mobile;
	private String email;
	private String address;
	private Integer localityId;
	private String city;
	private String state;
	private String country;
	private String zip;
	private Double latitude;
	private Double longitude;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Double openTime) {
		this.openTime = openTime;
	}

	public Double getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Double closeTime) {
		this.closeTime = closeTime;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getCuisines() {
		return cuisines;
	}

	public void setCuisines(String cuisines) {
		this.cuisines = cuisines;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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
