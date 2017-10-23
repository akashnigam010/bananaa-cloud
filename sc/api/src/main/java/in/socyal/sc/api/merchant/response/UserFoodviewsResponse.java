package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class UserFoodviewsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<UserFoodview> foodviews;

	public List<UserFoodview> getFoodviews() {
		return foodviews;
	}

	public void setFoodviews(List<UserFoodview> foodviews) {
		this.foodviews = foodviews;
	}
}
