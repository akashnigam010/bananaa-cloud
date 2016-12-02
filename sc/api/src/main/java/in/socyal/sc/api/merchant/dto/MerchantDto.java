package in.socyal.sc.api.merchant.dto;

import java.io.Serializable;

public class MerchantDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String imageUrl;
	private ContactDto contact;
	private AddressDto address;
	private Double openTime;
	private Double closeTime;
	private Double rating;
	private Integer checkins;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ContactDto getContact() {
		return contact;
	}

	public void setContact(ContactDto contact) {
		this.contact = contact;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
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

	public Integer getCheckins() {
		return checkins;
	}

	public void setCheckins(Integer checkins) {
		this.checkins = checkins;
	}
}
