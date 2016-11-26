package in.socyal.sc.api.personnel.dto;

public class FacebookUser {
	private String id;
	private String first_name;
	private String last_name;
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
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
