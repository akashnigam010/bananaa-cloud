package in.socyal.sc.api.login.dto;

public class LoginUserDto {
	private Integer id;
	private String firstName;
	private String lastName;
	private String imageUrl;
	private Integer userCheckins;
	private Boolean isFollow;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getUserCheckins() {
		return userCheckins;
	}

	public void setUserCheckins(Integer userCheckins) {
		this.userCheckins = userCheckins;
	}

	public Boolean getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}
}
