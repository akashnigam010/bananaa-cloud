package in.socyal.sc.api.merchant.dto;

import java.io.Serializable;

public class ContactDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String telephone;
	private String mobile;
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
