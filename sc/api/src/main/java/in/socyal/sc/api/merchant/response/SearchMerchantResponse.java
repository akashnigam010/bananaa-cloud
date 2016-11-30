package in.socyal.sc.api.merchant.response;

import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class SearchMerchantResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<MerchantDto> merchants;

	public List<MerchantDto> getMerchants() {
		if (merchants == null) {
			merchants = new ArrayList<>();
		}
		return merchants;
	}

	public void setMerchants(List<MerchantDto> merchants) {
		this.merchants = merchants;
	}
}
