package in.socyal.sc.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.restaurant.request.AllRestaurantsRequest;
import in.socyal.sc.api.restaurant.request.RestaurantDetailsRequest;
import in.socyal.sc.api.restaurant.response.AllRestaurants;
import in.socyal.sc.api.restaurant.response.AllRestaurantsResponse;
import in.socyal.sc.api.restaurant.response.RestaurantDetailsResponse;

@RestController
@RequestMapping(value = "/restaurant")
public class RestaurantService {
	@RequestMapping(value = "/fetchAll", method = RequestMethod.POST, headers = "Accept=application/json")
	public AllRestaurantsResponse fetchAllRestaurants(@RequestBody AllRestaurantsRequest request) {
		AllRestaurantsResponse response = new AllRestaurantsResponse();
		List<AllRestaurants> restaurants = new ArrayList<>();
		response.setRestaurants(restaurants);
		return response;
	}
	
	@RequestMapping(value = "/fetchDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public RestaurantDetailsResponse fetchRestaurantDetails(@RequestBody RestaurantDetailsRequest request) {
		RestaurantDetailsResponse response = new RestaurantDetailsResponse();
		return response;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, headers = "Accept=application/json")
	public AllRestaurantsResponse test() {
		AllRestaurantsResponse response = new AllRestaurantsResponse();
		return response;
	}
}
