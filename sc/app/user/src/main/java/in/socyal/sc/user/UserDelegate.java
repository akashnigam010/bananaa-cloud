package in.socyal.sc.user;

import in.socyal.sc.api.user.request.GetMyFriendsRequest;
import in.socyal.sc.api.user.request.GetPublicProfileRequest;
import in.socyal.sc.api.user.request.SaveRegistrationIdRequest;
import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.api.user.response.FriendResponse;
import in.socyal.sc.api.user.response.SaveRegistrationIdResponse;
import in.socyal.sc.api.user.response.SearchFriendResponse;
import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.helper.exception.BusinessException;

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
	 * Save RegistrationId details for logged in USER
	 * @return
	 * @throws BusinessException
	 */
	public SaveRegistrationIdResponse saveRegistrationId(SaveRegistrationIdRequest request) throws BusinessException;
}
