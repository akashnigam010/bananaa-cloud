package in.socyal.sc.api.login.dto;

public class FacebookUser {
	private String id;
	private String firstName;
	private String lastName;
	private String link;
	private String email;
	private FacebookPicture picture;
	private String gender;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLast_name(String lastName) {
		this.lastName = lastName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public FacebookPicture getPicture() {
		return picture;
	}

	public void setPicture(FacebookPicture picture) {
		this.picture = picture;
	}
}
