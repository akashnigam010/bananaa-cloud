package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import in.socyal.sc.persistence.type.BucketUrlType;

@Entity
@Table(name = "MERCHANT", schema = "bna")
public class MerchantEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "NAME_ID")
	private String nameId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	private AddressEntity address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTACT_ID")
	private ContactEntity contact;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	
	@Column(name = "THUMBNAIL")
	private String thumbnail;

	@Column(name = "AVERAGE_COST")
	private Double averageCost;

	@Column(name = "TYPE")
	private String type;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", referencedColumnName = "ID")
	private Set<TimingEntity> timings;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "merchant")
	@OrderBy("rating desc")
	private List<MerchantCuisineRatingEntity> cuisineRatings;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "merchant")
	@OrderBy("rating desc")
	private List<MerchantSuggestionRatingEntity> suggestionRatings;
	
	@Column(name = "CAN_EDIT")
	private Boolean canEdit;
	
	public MerchantEntity() {

	}

	public MerchantEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
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

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public String getImageUrl() {
		return BucketUrlType.URL_PREFIX.getUrlPrefix() + imageUrl;
	}

	//TODO : Fix this - do not store complete URL
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnail() {
		return BucketUrlType.URL_PREFIX.getUrlPrefix() + thumbnail;
	}

	//TODO : Fix this - do not store complete thumbnail url
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Double averageCost) {
		this.averageCost = averageCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<TimingEntity> getTimings() {
		return timings;
	}

	public void setTimings(Set<TimingEntity> timings) {
		this.timings = timings;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<MerchantCuisineRatingEntity> getCuisineRatings() {
		return cuisineRatings;
	}

	public void setCuisineRatings(List<MerchantCuisineRatingEntity> cuisineRatings) {
		this.cuisineRatings = cuisineRatings;
	}

	public List<MerchantSuggestionRatingEntity> getSuggestionRatings() {
		return suggestionRatings;
	}

	public void setSuggestionRatings(List<MerchantSuggestionRatingEntity> suggestionRatings) {
		this.suggestionRatings = suggestionRatings;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}
}
