package in.socyal.sc.api.user.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class UserProfileResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String primaryImageUrl;
	private String email;
	private String facebookId;
	private Integer userCheckins;

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

	public String getPrimaryImageUrl() {
		return primaryImageUrl;
	}

	public void setPrimaryImageUrl(String primaryImageUrl) {
		this.primaryImageUrl = primaryImageUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Integer getUserCheckins() {
		return userCheckins;
	}

	public void setUserCheckins(Integer userCheckins) {
		this.userCheckins = userCheckins;
	}
}
