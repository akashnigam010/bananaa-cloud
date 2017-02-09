package in.socyal.sc.core;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.user.request.FollowRequest;
import in.socyal.sc.api.user.request.GetMyFriendsRequest;
import in.socyal.sc.api.user.request.GetPublicProfileRequest;
import in.socyal.sc.api.user.request.SaveRegistrationIdRequest;
import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.api.user.request.UnFollowRequest;
import in.socyal.sc.api.user.response.FollowResponse;
import in.socyal.sc.api.user.response.FriendResponse;
import in.socyal.sc.api.user.response.SaveRegistrationIdResponse;
import in.socyal.sc.api.user.response.SearchFriendResponse;
import in.socyal.sc.api.user.response.SearchFriendToTagResponse;
import in.socyal.sc.api.user.response.UnFollowResponse;
import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.core.validation.UserValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.user.UserDelegate;

@RestController
@RequestMapping(value = "/socyal/user")
public class UserService {
	private static final Logger LOG = Logger.getLogger(UserService.class);
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
			LOG.info("Get my profile request");
			response = userDelegate.getProfile();
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getPublicProfile", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserProfileResponse getPublicProfile(@RequestBody GetPublicProfileRequest request) {
		JsonHelper.logRequest(request, UserService.class, "/user/getPublicProfile");
		UserProfileResponse response = new UserProfileResponse();
		try {
			validator.validateGetPublicProfileRequest(request);
			response = userDelegate.getPublicProfile(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/searchFriendsToTag", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchFriendToTagResponse searchFriendsToTag(@RequestBody SearchFriendRequest request) {
		SearchFriendToTagResponse response = new SearchFriendToTagResponse();
		try {
			validator.validateSearchUserRequest(request);
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				response = userDelegate.searchFriendsToTag(request);
			}
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	@RequestMapping(value = "/searchFriends", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchFriendResponse searchFriends(@RequestBody SearchFriendRequest request) {
		JsonHelper.logRequest(request, UserService.class, "/user/searchFriends");
		SearchFriendResponse response = new SearchFriendResponse();
		try {
			// this is a semi-authorized call. Validator doesn't check for logged in user.
			// Service response wouldn't contain people whom user follows when not logged in
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
		JsonHelper.logRequest(request, UserService.class, "/user/getMyFriends");
		FriendResponse response = new FriendResponse();
		try {
			validator.validateGetMyFriendsRequest(request);
			response = userDelegate.getMyFriends(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	/**
	 * This method is used to save REGISTRATION_ID for logged in USER
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveRegistrationId", method = RequestMethod.POST, headers = "Accept=application/json")
	public SaveRegistrationIdResponse saveRegistrationId(@RequestBody SaveRegistrationIdRequest request) {
		JsonHelper.logRequest(request, UserService.class, "/user/saveRegistrationId");
		SaveRegistrationIdResponse response = new SaveRegistrationIdResponse();
		try {
			validator.validateRegistrationIdRequest(request);
			response = userDelegate.saveRegistrationId(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/follow", method = RequestMethod.POST, headers = "Accept=application/json")
	public FollowResponse follow(@RequestBody FollowRequest request) {
		JsonHelper.logRequest(request, UserService.class, "/user/follow");
		FollowResponse response = new FollowResponse();
		try {
			validator.validateFollowRequest(request);
			response = userDelegate.follow(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/unFollow", method = RequestMethod.POST, headers = "Accept=application/json")
	public UnFollowResponse unFollow(@RequestBody UnFollowRequest request) {
		JsonHelper.logRequest(request, UserService.class, "/user/unFollow");
		UnFollowResponse response = new UnFollowResponse();
		try {
			validator.validateUnFollowRequest(request);
			response = userDelegate.unFollow(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}
