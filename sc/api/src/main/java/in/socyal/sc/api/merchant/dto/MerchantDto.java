package in.socyal.sc.api.merchant.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class MerchantDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nameId;
	private String name;
	private String imageUrl;
	private String thumbnail;
	private ContactDto contact;
	private AddressDto address;
	private List<TimingDto> timings;
	private Double averageCost;
	private List<String> types;
	private String merchantUrl;

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

	public List<TimingDto> getTimings() {
		return timings;
	}

	public void setTimings(List<TimingDto> timings) {
		this.timings = timings;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
