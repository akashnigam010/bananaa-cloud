package in.socyal.sc.api.recommendation.request;

public class GetRecommendationRequest {
	private Integer merchantId;
	private Integer page;

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
