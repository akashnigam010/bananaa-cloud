package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DISH", schema = "bna")
public class DishEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NAME_ID")
	private String nameId;

	@Column(name = "SUGGESTION_ID")
	private Integer suggestionId;

	@Column(name = "CUISINE_ID")
	private Integer cuisineId;

	@ManyToOne
	@JoinColumn(name = "MERCHANT_ID")
	private MerchantEntity merchant;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "IS_ACTIVE")
	private Boolean isActive;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
	private List<RecommendationEntity> recommendations;

	public DishEntity() {

	}

	public DishEntity(Integer id) {
		this.id = id;
	}

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

	public Integer getSuggestionId() {
		return suggestionId;
	}

	public void setSuggestionId(Integer suggestionId) {
		this.suggestionId = suggestionId;
	}

	public Integer getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(Integer cuisineId) {
		this.cuisineId = cuisineId;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
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
}
