package in.socyal.sc.api.restaurant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class RestaurantListResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<RestaurantDto> restaurants;

	public List<RestaurantDto> getRestaurants() {
		if (restaurants == null) {
			return new ArrayList<>();
		}
		return restaurants;
	}

	public void setRestaurants(List<RestaurantDto> restaurants) {
		this.restaurants = restaurants;
	}
}
