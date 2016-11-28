package in.socyal.sc.api.restaurant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class AllRestaurantsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<RestaurantResponse> restaurants;

	public List<RestaurantResponse> getRestaurants() {
		if (restaurants == null) {
			return new ArrayList<>();
		}
		return restaurants;
	}

	public void setRestaurants(List<RestaurantResponse> restaurants) {
		this.restaurants = restaurants;
	}
}
