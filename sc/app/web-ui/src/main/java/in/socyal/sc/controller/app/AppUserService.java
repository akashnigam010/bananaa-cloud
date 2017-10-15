package in.socyal.sc.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.api.user.request.SavePreferencesRequest;
import in.socyal.sc.user.UserDelegate;

@RestController
@RequestMapping(value = "/bna/user")
public class AppUserService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	UserDelegate delegate;

	@RequestMapping(value = "/getUserPreferences", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse searchCuisine(@RequestBody SearchRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			delegate.getUserPreferences(request.getMerchantId());
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/saveUserPreferences", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveUserPreferences(@RequestBody SavePreferencesRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			delegate.saveUserPreferences(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
