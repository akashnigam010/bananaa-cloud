package in.socyal.sc.api.merchant.business.dto;

import java.io.Serializable;

import in.socyal.sc.api.merchant.dto.MerchantDto;

public class MerchantLoginDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private MerchantDto merchant;
	private Integer deviceId;
	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	private String registrationId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MerchantDto getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDto merchant) {
		this.merchant = merchant;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
}
