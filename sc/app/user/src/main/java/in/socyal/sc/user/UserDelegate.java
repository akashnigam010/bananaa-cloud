package in.socyal.sc.user;

import in.socyal.sc.api.helper.exception.BusinessException;
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

public interface UserDelegate {
	/**
	 * Fetch logged in user profile details
	 * @return
	 * @throws BusinessException
	 */
	public UserProfileResponse getProfile() throws BusinessException;
	
	/**
	 * Fetch list of friends matching search pattern
	 * @return
	 * @throws BusinessException
	 */
	public SearchFriendResponse searchFriends(SearchFriendRequest request) throws BusinessException;

	/**
	 * Fetch list of my friends - paginated
	 * @return
	 * @throws BusinessException
	 */
	public FriendResponse getMyFriends(GetMyFriendsRequest request);

	/**
	 * Fetch public profile details
	 * @return
	 * @throws BusinessException
	 */
	public UserProfileResponse getPublicProfile(GetPublicProfileRequest request);

	/**
	 * Fetch list of friends to tag
	 * @return
	 * @throws BusinessException
	 */
	SearchFriendToTagResponse searchFriendsToTag(SearchFriendRequest request) throws BusinessException;
	 
	/** Save RegistrationId details for logged in USER
	 * @return
	 * @throws BusinessException
	 */
	public SaveRegistrationIdResponse saveRegistrationId(SaveRegistrationIdRequest request) throws BusinessException;

	/** 
	 * Method for a user to follow another user
	 * @param request
	 * @return
	 */
	public FollowResponse follow(FollowRequest request) throws BusinessException;

	/** 
	 * Method for a user to unfollow another user
	 * @param request
	 * @return
	 */
	public UnFollowResponse unFollow(UnFollowRequest request) throws BusinessException;
}
