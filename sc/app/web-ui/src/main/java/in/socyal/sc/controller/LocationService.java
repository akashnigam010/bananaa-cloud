package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.location.request.SearchLocationRequest;
import in.socyal.sc.api.location.response.GetLocalitiesResponse;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.location.LocationDelegate;

@RestController
@RequestMapping(value = "/socyal/location")
public class LocationService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	ResponseHelper helper;
	@Autowired
	LocationDelegate delegate;

	@RequestMapping(value = "/getLocalities", method = RequestMethod.GET, headers = "Accept=application/json")
	public GetLocalitiesResponse getLocalities() {
		GetLocalitiesResponse response = delegate.getLocalities();
		return helper.success(response);
	}

	@RequestMapping(value = "/searchLocality", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetLocalitiesResponse searchLocality(@RequestBody SearchLocationRequest request) {
		JsonHelper.logRequest(request, LocationService.class, "/location/searchLocality");
		GetLocalitiesResponse response = new GetLocalitiesResponse();
		if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
			response = delegate.searchLocalities(request);
		}
		return helper.success(response);
	}
}
