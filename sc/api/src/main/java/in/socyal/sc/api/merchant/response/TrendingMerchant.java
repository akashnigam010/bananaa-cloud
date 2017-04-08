package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

public class TrendingMerchant  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String shortAddress;
	private Long recommendations;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
