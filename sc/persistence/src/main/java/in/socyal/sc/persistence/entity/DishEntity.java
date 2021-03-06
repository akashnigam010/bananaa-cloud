package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import in.socyal.sc.persistence.type.BucketUrlType;

@Entity
@Table(name = "DISH", schema = "bna")
public class DishEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "NAME_ID")
	private String nameId;
	
	@Column(name = "COST")
	private String cost;
	
	@Column(name = "VEGNONVEG")
	private Integer vegnonveg;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "DISH_SUGGESTION_MAPPING", joinColumns = { @JoinColumn(name = "DISH_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "SUGGESTION_ID") })
	private List<SuggestionEntity> suggestions;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "DISH_CUISINE_MAPPING", joinColumns = { @JoinColumn(name = "DISH_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CUISINE_ID") })
	private List<CuisineEntity> cuisines;

	@ManyToOne
	@JoinColumn(name = "MERCHANT_ID")
	private MerchantEntity merchant;
	
	@ManyToOne
	@JoinColumn(name = "IMAGE_ID")
	private DishImageEntity image;

	@Column(name = "IS_ACTIVE")
	private Boolean isActive;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
	private List<RecommendationEntity> recommendations;
	
	@Column(name = "RATING")
	private Float rating;

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public DishEntity() {

	}

	public DishEntity(Integer id) {
		super.setId(id);
	}
	
	public DishEntity(Calendar createdDateTime, Calendar updatedDateTime) {
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
		
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public Integer getVegnonveg() {
		return vegnonveg;
	}

	public void setVegnonveg(Integer vegnonveg) {
		this.vegnonveg = vegnonveg;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	public String getImageUrl() {
		return BucketUrlType.URL_PREFIX.getUrlPrefix() + this.image.getImage();
	}

	//TODO : Fix this
	public void setImageUrl(String imageUrl) {
		//this.imageUrl = imageUrl;
	}
	
	/**
	 * Do not use this directly, instead use {@link #getImageUrl()} and {@link #getThumbnail()}}
	 * @return
	 */
	@Deprecated
	public DishImageEntity getImage() {
		return image;
	}

	public void setImage(DishImageEntity image) {
		this.image = image;
	}

	public String getThumbnail() {
		return BucketUrlType.URL_PREFIX.getUrlPrefix() + this.image.getThumbnail();
	}

	//TODO : Fix this
	public void setThumbnail(String thumbnail) {
		//this.thumbnail = thumbnail;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<RecommendationEntity> getRecommendations() {
		if (this.recommendations == null) {
			this.recommendations = new ArrayList<>();
		}
		return recommendations;
	}

	public void setRecommendations(List<RecommendationEntity> recommendations) {
		this.recommendations = recommendations;
	}

	public List<SuggestionEntity> getSuggestions() {
		if (this.suggestions == null) {
			this.suggestions = new ArrayList<>();
		}
		return suggestions;
	}

	public void setSuggestions(List<SuggestionEntity> suggestions) {
		this.suggestions = suggestions;
	}

	public List<CuisineEntity> getCuisines() {
		if (this.cuisines == null) {
			this.cuisines = new ArrayList<>();
		}
		return cuisines;
	}

	public void setCuisines(List<CuisineEntity> cuisines) {
		this.cuisines = cuisines;
	}
}
