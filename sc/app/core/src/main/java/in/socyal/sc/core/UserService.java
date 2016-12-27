package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.user.UserDelegate;

@RestController
@RequestMapping(value = "/user")
@PreAuthorize("hasAuthority('USER')")
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
}
