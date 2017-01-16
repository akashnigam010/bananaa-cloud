package in.socyal.sc.api.merchant.dto;

import java.io.Serializable;

public class ContactDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String phone1;
	private String phone2;
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
