package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class GetTrendingMerchantsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<TrendingMerchant> merchants;

	public List<TrendingMerchant> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<TrendingMerchant> merchants) {
		this.merchants = merchants;
	}
}
