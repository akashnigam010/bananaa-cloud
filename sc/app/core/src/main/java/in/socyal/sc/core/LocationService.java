package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.location.request.SearchLocationRequest;
import in.socyal.sc.api.location.response.GetLocalitiesResponse;
import in.socyal.sc.core.validation.LocationValidator;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.location.LocationDelegate;

@RestController
@RequestMapping(value = "/location")
public class LocationService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	LocationDelegate delegate;
	@Autowired
	LocationValidator validator;

	@RequestMapping(value = "/getLocalities", method = RequestMethod.GET, headers = "Accept=application/json")
	public GetLocalitiesResponse getLocalities() {
		GetLocalitiesResponse response = delegate.getLocalities();
		return helper.success(response);
	}

	@RequestMapping(value = "/searchLocality", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetLocalitiesResponse searchLocality(@RequestBody SearchLocationRequest request) {
		GetLocalitiesResponse response = new GetLocalitiesResponse();
		try {
			validator.validateSearchLocationRequest(request);
			response = delegate.searchLocalities(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
