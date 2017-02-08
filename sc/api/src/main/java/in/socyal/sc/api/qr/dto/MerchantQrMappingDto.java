package in.socyal.sc.api.qr.dto;

import java.io.Serializable;

import in.socyal.sc.api.merchant.dto.MerchantDto;

public class MerchantQrMappingDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String qrCode;
	private MerchantDto merchant;
	private String cardId;
	private Boolean status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public MerchantDto getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDto merchant) {
		this.merchant = merchant;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
