package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MERCHANT", schema = "Socyal")
public class MerchantEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTACT_ID")
	private ContactEntity contact;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	private AddressEntity address;
	
	@Column(name = "OPEN_TIME")
	private Double openTime;
	
	@Column(name = "CLOSE_TIME")
	private Double closeTime;
	
	@Column(name = "RATING")
	private Double rating;
	
	@Column(name = "CHECKINS")
	private Integer checkins;

	@Column(name = "CUISINES")
	private String cuisines;
	
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

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
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

	public String getCuisines() {
		return cuisines;
	}

	public void setCuisines(String cuisines) {
		this.cuisines = cuisines;
	}
}
