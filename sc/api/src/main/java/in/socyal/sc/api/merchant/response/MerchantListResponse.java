package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class MerchantListResponse extends GenericResponse implements Serializable {
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
