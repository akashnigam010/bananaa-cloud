package in.socyal.sc.persistence.entity;

import java.io.Serializable;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import in.socyal.sc.persistence.type.BucketUrlType;

@Entity
@Table(name = "USER", schema = "bna")
public class UserEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "UID")
	private String uid;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "NAME_ID")
	private String nameId;
	
	@Column(name = "CREDIBILITY")
	private Float credibility;
	
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "EMAIL")
	private String email;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_STATUS_ID")
	private UserStatusEntity status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<RecommendationEntity> recommendations;
	
	@ManyToOne
	@JoinColumn(name = "VEGNONVEG_ID")
	private VegnonvegEntity vegnonvegPreference;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_SUGGESTION_PREF_MAPPING", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "SUGGESTION_ID") })
	@OrderBy
	private List<SuggestionEntity> suggestionPreferences;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_CUISINE_PREF_MAPPING", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CUISINE_ID") })
	@OrderBy
	private List<CuisineEntity> cuisinePreferences;

	public UserEntity() {

	}

	public UserEntity(Integer id) {
		super.setId(id);
	}
	

	public UserEntity(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public Float getCredibility() {
		return credibility;
	}

	public void setCredibility(Float credibility) {
		this.credibility = credibility;
	}

	public String getImageUrl() {
		return BucketUrlType.USER_PREFIX.getUrlPrefix() + imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<RecommendationEntity> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<RecommendationEntity> recommendations) {
		this.recommendations = recommendations;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SuggestionEntity> getSuggestionPreferences() {
		return suggestionPreferences;
	}

	public void setSuggestionPreferences(List<SuggestionEntity> suggestionPreferences) {
		this.suggestionPreferences = suggestionPreferences;
	}

	public List<CuisineEntity> getCuisinePreferences() {
		return cuisinePreferences;
	}

	public void setCuisinePreferences(List<CuisineEntity> cuisinePreferences) {
		this.cuisinePreferences = cuisinePreferences;
	}

	public VegnonvegEntity getVegnonvegPreference() {
		return vegnonvegPreference;
	}

	public void setVegnonvegPreference(VegnonvegEntity vegnonvegPreference) {
		this.vegnonvegPreference = vegnonvegPreference;
	}

	public UserStatusEntity getStatus() {
		return status;
	}

	public void setStatus(UserStatusEntity status) {
		this.status = status;
	}
}
