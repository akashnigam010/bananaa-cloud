package in.socyal.sc.api.items.request;

public class GetPopularItemsRequest {
	private Integer merchantId;
	private Integer resultsPerPage;
	private Integer page;

	public GetPopularItemsRequest() {}

	public GetPopularItemsRequest(Integer merchantId, Integer resultsPerPage, Integer page) {
		this.merchantId = merchantId;
		this.resultsPerPage = resultsPerPage;
		this.page = page;
	}

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

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
}
