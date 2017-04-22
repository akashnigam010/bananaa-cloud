package in.socyal.sc.api.user.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import in.socyal.sc.api.recommendation.dto.RecommendationDto;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String uid;
	private String firstName;
	private String lastName;
	private String nameId;
	private String imageUrl;
	private String email;
	private Calendar createdDateTime;
	private Calendar updatedDateTime;
	private String userUrl;
	private List<RecommendationDto> recommendations;
	private Integer totalRecommendations;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getImageUrl() {
		return imageUrl;
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

	public Calendar getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Calendar getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Calendar updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public boolean equals(Object obj) {
		UserDto user = (UserDto) obj;
		return (this.id == user.id);
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", uid=" + uid + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", nameId=" + nameId + ", imageUrl=" + imageUrl + ", email=" + email + "]";
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public List<RecommendationDto> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<RecommendationDto> recommendations) {
		this.recommendations = recommendations;
	}

	public Integer getTotalRecommendations() {
		return totalRecommendations;
	}

	public void setTotalRecommendations(Integer totalRecommendations) {
		this.totalRecommendations = totalRecommendations;
	}
}
