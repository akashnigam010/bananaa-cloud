package in.socyal.sc.api.restaurant.request;

import java.io.Serializable;

public class RestaurantDetailsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer restaurantId;

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
}
