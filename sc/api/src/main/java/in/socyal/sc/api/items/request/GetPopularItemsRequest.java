package in.socyal.sc.api.items.request;

public class GetPopularItemsRequest {
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
