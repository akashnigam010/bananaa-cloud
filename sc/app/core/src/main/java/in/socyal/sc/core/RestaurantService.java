package in.socyal.sc.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.restaurant.dto.RestaurantDetailsRequestDto;
import in.socyal.sc.api.restaurant.dto.RestaurantDetailsResponseDto;
import in.socyal.sc.api.restaurant.request.GetRestaurantListRequest;
import in.socyal.sc.api.restaurant.request.RestaurantDetailsRequest;
import in.socyal.sc.api.restaurant.response.RestaurantListResponse;
import in.socyal.sc.api.restaurant.response.RestaurantDetailsResponse;
import in.socyal.sc.app.restaurant.RestaurantDelegate;
import in.socyal.sc.core.mapper.RestaurantServiceMapper;
import in.socyal.sc.helper.ResponseHelper;

@RestController
@RequestMapping(value = "/restaurant")
public class RestaurantService {
	@Autowired RestaurantDelegate delegate;
	@Autowired RestaurantServiceMapper mapper;
	@Autowired ResponseHelper responseHelper;
	
	@RequestMapping(value = "/getRestaurants", method = RequestMethod.POST, headers = "Accept=application/json")
	public RestaurantListResponse getRestaurants(@RequestBody GetRestaurantListRequest request) {
		RestaurantListResponse response = new RestaurantListResponse();
		RestaurantDetailsRequestDto requestDto = new RestaurantDetailsRequestDto();
		mapper.map(request, requestDto);
		List<RestaurantDetailsResponseDto> restaurants = delegate.getRestaurants(requestDto);
		mapper.map(restaurants, response);
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/getRestaurantDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public RestaurantDetailsResponse getRestaurantDetails(@RequestBody RestaurantDetailsRequest request) {
		RestaurantDetailsResponse response = new RestaurantDetailsResponse();
		RestaurantDetailsResponseDto restaurant = delegate.getRestaurantDetails(request.getId());
		mapper.map(restaurant, response);
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, headers = "Accept=application/json")
	public RestaurantListResponse test() {
		RestaurantListResponse response = new RestaurantListResponse();
		return response;
	}
}
