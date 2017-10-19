package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class ItemRecommendationResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private MyFoodview recommendation;
	private boolean recommended = Boolean.FALSE;

	public MyFoodview getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(MyFoodview recommendation) {
		this.recommendation = recommendation;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.recommended = isRecommended;
	}
}
