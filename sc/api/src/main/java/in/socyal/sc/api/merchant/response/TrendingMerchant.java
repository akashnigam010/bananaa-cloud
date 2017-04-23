package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

public class TrendingMerchant  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String nameId;
	private String shortAddress;
	private Long recommendations;
	private String thumbnail;
	private String merchantUrl;

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

	public String getShortAddress() {
		return shortAddress;
	}

	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}

	public Long getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Long recommendations) {
		this.recommendations = recommendations;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String imageUrl) {
		this.thumbnail = imageUrl;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}
}
