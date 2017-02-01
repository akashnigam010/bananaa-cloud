package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MERCHANT_LOGIN", schema = "Socyal")
public class MerchantLoginEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "MERCHANT_ID")
	private MerchantEntity merchant;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "REGISTRATION_ID")
	private String registrationId;
	
	@Column(name = "UPDATED_DATETIME")
	private Calendar updatedDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public Calendar getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Calendar updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
}
