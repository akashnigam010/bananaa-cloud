package in.socyal.sc.app.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.socyal.sc.api.restaurant.dto.RestaurantDetailsRequestDto;
import in.socyal.sc.api.restaurant.dto.RestaurantDetailsResponseDto;

@Service
public class RestaurantDelegate {
	public List<RestaurantDetailsResponseDto> getRestaurants(RestaurantDetailsRequestDto request) {
		List<RestaurantDetailsResponseDto> restaurants = new ArrayList<>();
		return restaurants;
	}
	
	public RestaurantDetailsResponseDto getRestaurantDetails(Integer restaurantId) {
		RestaurantDetailsResponseDto restaurant = new RestaurantDetailsResponseDto();
		return restaurant;
	}
}
