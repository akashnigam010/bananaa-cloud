package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.user.request.GetMyFriendsRequest;
import in.socyal.sc.api.user.request.GetPublicProfileRequest;
import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.api.user.response.FriendResponse;
import in.socyal.sc.api.user.response.SearchFriendResponse;
import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.core.validation.UserValidator;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.user.UserDelegate;

@RestController
@RequestMapping(value = "/user")
public class UserService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	UserDelegate userDelegate;
	@Autowired
	ResponseHelper responseHelper;
	@Autowired
	UserValidator validator;

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
	
	@RequestMapping(value = "/getPublicProfile", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserProfileResponse getPublicProfile(@RequestBody GetPublicProfileRequest request) {
		UserProfileResponse response = new UserProfileResponse();
		try {
			validator.validateGetPublicProfileRequest(request);
			response = userDelegate.getPublicProfile(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	@RequestMapping(value = "/searchFriends", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchFriendResponse searchFriends(@RequestBody SearchFriendRequest request) {
		SearchFriendResponse response = new SearchFriendResponse();
		try {
			validator.validateSearchFriendRequest(request);
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				response = userDelegate.searchFriends(request);
			}
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getMyFriends", method = RequestMethod.POST, headers = "Accept=application/json")
	public FriendResponse getMyFriends(@RequestBody GetMyFriendsRequest request) {
		FriendResponse response = new FriendResponse();
		try {
			validator.validateGetMyFriendsRequest(request);
			response = userDelegate.getMyFriends(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}
