package in.socyal.sc.api.merchant.response;

import java.io.Serializable;

import in.socyal.sc.api.response.GenericResponse;

public class ItemRecommendationResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Foodview recommendation;
	private boolean recommended = Boolean.FALSE;

	public Foodview getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Foodview recommendation) {
		this.recommendation = recommendation;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.recommended = isRecommended;
	}
}
