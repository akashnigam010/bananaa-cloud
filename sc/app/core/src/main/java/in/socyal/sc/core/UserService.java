package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.user.request.SearchUserRequest;
import in.socyal.sc.api.user.response.SearchUserResponse;
import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.user.UserDelegate;

@RestController
@RequestMapping(value = "/user")
public class UserService {
	@Autowired UserDelegate userDelegate;
	@Autowired ResponseHelper responseHelper;
	
	@RequestMapping(value = "/getMyProfile", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserProfileResponse getProfile() {
		UserProfileResponse response = new UserProfileResponse();
		try {
			response = userDelegate.getProfile();
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/searchUsers", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchUserResponse searchUsers(@RequestBody SearchUserRequest request) {
		SearchUserResponse response = new SearchUserResponse();
		try {
			response = userDelegate.searchUsers(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}
