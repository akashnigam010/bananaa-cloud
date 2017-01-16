package in.socyal.sc.api.checkin.dto;

public class UserDetailsDto {
	private Integer id;
	private String name;
	private Integer userCheckins;
	private String imageUrl;

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

	public Integer getUserCheckins() {
		return userCheckins;
	}

	public void setUserCheckins(Integer userCheckins) {
		this.userCheckins = userCheckins;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
