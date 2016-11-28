package in.socyal.sc.core.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.restaurant.dto.RestaurantDetailsRequestDto;
import in.socyal.sc.api.restaurant.dto.RestaurantDetailsResponseDto;
import in.socyal.sc.api.restaurant.request.GetRestaurantListRequest;
import in.socyal.sc.api.restaurant.response.RestaurantListResponse;
import in.socyal.sc.api.restaurant.response.LocationResponse;
import in.socyal.sc.api.restaurant.response.RestaurantDetailsResponse;
import in.socyal.sc.api.restaurant.response.RestaurantDto;

@Component
public class RestaurantServiceMapper {

	public void map(GetRestaurantListRequest request, RestaurantDetailsRequestDto requestDto) {
	}

	public void map(List<RestaurantDetailsResponseDto> from, RestaurantListResponse to) {
		List<RestaurantDto> restaurants = new ArrayList<>();
		RestaurantDto restaurant1 = new RestaurantDto();
		restaurant1.setId(3241);
		restaurant1.setCheckins(56);
		restaurant1.setDistance(5.45);
		restaurant1.setImageUrl("http://www.whitebay.in/images/fusion9.png");
		restaurant1.setIsOpen(Boolean.TRUE);
		restaurant1.setName("Fusion 9");
		restaurant1.setRating(4.2);
		restaurant1.setShortAddress("Hitech City, Hyderabad");
		RestaurantDto restaurant2 = new RestaurantDto();
		restaurant2.setId(2345);
		restaurant2.setCheckins(32);
		restaurant2.setDistance(6.20);
		restaurant2.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		restaurant2.setIsOpen(Boolean.TRUE);
		restaurant2.setName("Heartcup Cafe Coffee");
		restaurant2.setRating(3.8);
		restaurant2.setShortAddress("Madhapur, Hyderabad");
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
		to.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
		to.setIsOpen(Boolean.TRUE);
		to.setOpenTime("10:30 AM to 12 Midnight");
		LocationResponse location = new LocationResponse();
		location.setLatitude(76.234567);
		location.setLongitude(78.398762);
		to.setLocation(location);
		to.setLongAddress("3rd Floor 136/137, Ancis Eco Grand, Near Wipro Lake, Nanakramguda, Financial District, Hitech City, Hyderabad");
		to.setName("Fusion 9");
		to.setShortAddress("Hitech City, Hyderabad");
		to.setType(Collections.singletonList("Fine Dining"));
	}
}
