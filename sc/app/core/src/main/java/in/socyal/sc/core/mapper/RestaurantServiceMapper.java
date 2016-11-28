package in.socyal.sc.core.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.restaurant.dto.RestaurantDetailsRequestDto;
import in.socyal.sc.api.restaurant.dto.RestaurantDetailsResponseDto;
import in.socyal.sc.api.restaurant.request.AllRestaurantsRequest;
import in.socyal.sc.api.restaurant.response.AllRestaurantsResponse;
import in.socyal.sc.api.restaurant.response.LocationResponse;
import in.socyal.sc.api.restaurant.response.RestaurantDetailsResponse;
import in.socyal.sc.api.restaurant.response.RestaurantResponse;

@Component
public class RestaurantServiceMapper {

	public void map(AllRestaurantsRequest request, RestaurantDetailsRequestDto requestDto) {
	}

	public void map(List<RestaurantDetailsResponseDto> from, AllRestaurantsResponse to) {
		List<RestaurantResponse> restaurants = new ArrayList<>();
		RestaurantResponse restaurant1 = new RestaurantResponse();
		restaurant1.setId(3241);
		restaurant1.setCheckins(56);
		restaurant1.setDistance(5.45);
		restaurant1.setImageUrl("http://www.google.co.in");
		restaurant1.setIsOpen(Boolean.TRUE);
		restaurant1.setName("WHITEBOARD PARTYHOLICS CAFE");
		restaurant1.setRating(4.2);
		restaurant1.setShortAddress("Hitech City, Hyderabad");
		RestaurantResponse restaurant2 = new RestaurantResponse();
		restaurant2.setId(2345);
		restaurant2.setCheckins(32);
		restaurant2.setDistance(6.20);
		restaurant2.setImageUrl("http://www.fb.com");
		restaurant2.setIsOpen(Boolean.TRUE);
		restaurant2.setName("FIREWATER RESTAURANT");
		restaurant2.setRating(3.8);
		restaurant2.setShortAddress("madhapur, Hyderabad");
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		to.setRestaurants(restaurants);
	}

	public void map(RestaurantDetailsResponseDto from, RestaurantDetailsResponse to) {
		to.setAverageCost(200.00);
		to.setCheckins(24);
		List<String> cuisines = new ArrayList<>();
		cuisines.add("Continental");
		cuisines.add("Indian");
		cuisines.add("Thai");
		to.setCuisines(cuisines);
		to.setDistance(3.46);
		to.setId(121);
		to.setImageUrl("https://flickr.com");
		to.setIsOpen(Boolean.TRUE);
		LocationResponse location = new LocationResponse();
		location.setLatitude(76.234567);
		location.setLongitude(78.398762);
		to.setLocation(location);
		to.setLongAddress("INORBIT MALL, Hitech city, Hyderabad - 500084");
		to.setName("FUSION 9 LOUNGE & BAR");
		to.setShortAddress("Hyderabad");
		to.setTimings(new Date());
		to.setType(Collections.singletonList("PREMIUM"));
	}
}
