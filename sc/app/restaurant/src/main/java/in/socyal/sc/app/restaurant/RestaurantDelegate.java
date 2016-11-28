package in.socyal.sc.app.restaurant;

import java.util.List;

import in.socyal.sc.api.restaurant.dto.RestaurantDetailsRequestDto;
import in.socyal.sc.api.restaurant.dto.RestaurantDetailsResponseDto;

public interface RestaurantDelegate {
	public List<RestaurantDetailsResponseDto> getRestaurants(RestaurantDetailsRequestDto request);
	public RestaurantDetailsResponseDto getRestaurantDetails(Integer restaurantId);
}
