package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class GetMerchantListResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MerchantResponse> merchants;

	public List<MerchantResponse> getMerchants() {
		if (merchants == null) {
			merchants = new ArrayList<>();
		}
		return merchants;
	}

	public void setMerchants(List<MerchantResponse> merchants) {
		this.merchants = merchants;
	}
}
