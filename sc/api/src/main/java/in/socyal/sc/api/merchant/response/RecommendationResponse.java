package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class RecommendationResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Recommendation> recommendations;
	private boolean addMore = Boolean.TRUE;

	public List<Recommendation> getRecommendations() {
		if (this.recommendations == null) {
			this.recommendations = new ArrayList<>();
		}
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	public boolean isAddMore() {
		return addMore;
	}

	public void setAddMore(boolean addMore) {
		this.addMore = addMore;
	}

}
