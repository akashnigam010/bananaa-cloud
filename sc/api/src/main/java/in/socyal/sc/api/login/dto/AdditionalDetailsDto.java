package in.socyal.sc.api.login.dto;

public class AdditionalDetailsDto {
	private String rateUsLink;
	private String aboutUsLink;
	private String contactUsMail;

	public String getRateUsLink() {
		return rateUsLink;
	}

	public void setRateUsLink(String rateUsLink) {
		this.rateUsLink = rateUsLink;
	}

	public String getAboutUsLink() {
		return aboutUsLink;
	}

	public void setAboutUsLink(String aboutUsLink) {
		this.aboutUsLink = aboutUsLink;
	}

	public String getContactUsMail() {
		return contactUsMail;
	}

	public void setContactUsMail(String contactUsMail) {
		this.contactUsMail = contactUsMail;
	}
}
