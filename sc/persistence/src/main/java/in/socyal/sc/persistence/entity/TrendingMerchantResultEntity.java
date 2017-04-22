package in.socyal.sc.persistence.entity;

public class TrendingMerchantResultEntity {
	private Integer merchantId;
	private Long recommendations;
	private MerchantEntity merchant;

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Long getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Long recommendations) {
		this.recommendations = recommendations;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}
}
