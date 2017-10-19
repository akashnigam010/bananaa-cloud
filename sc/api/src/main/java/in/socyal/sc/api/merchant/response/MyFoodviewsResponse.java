package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class MyFoodviewsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MyFoodview> recommendations;
	private String merchantName;

	public List<MyFoodview> getRecommendations() {
		if (this.recommendations == null) {
			this.recommendations = new ArrayList<>();
		}
		return recommendations;
	}

	public void setRecommendations(List<MyFoodview> recommendations) {
		this.recommendations = recommendations;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
