package in.socyal.sc.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.user.dto.UserDto;
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
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.UserDao;
import in.socyal.sc.persistence.UserFollowerMappingDao;
import in.socyal.sc.user.mapper.UserMapper;
import in.socyal.sc.user.type.UserErrorCodeType;

@Service
public class UserDelegateImpl implements UserDelegate {
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	UserDao userDao;
	@Autowired
	CheckinDao checkinDao;
	@Autowired
	UserMapper mapper;
	@Autowired
	UserFollowerMappingDao userFollowerDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserProfileResponse getProfile() throws BusinessException {
		authorizeUser();
		UserProfileResponse response = new UserProfileResponse();
		Integer userId = Integer.valueOf(jwtHelper.getUserName());
		// Fetch user details
		UserDto user = userDao.fetchUser(userId);
		// Fetch user checkin count
		Integer userCheckinCount = checkinDao.getUserCheckinCount(userId);
		response.setUser(mapper.map(user, userCheckinCount));
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserProfileResponse getPublicProfile(GetPublicProfileRequest request) throws BusinessException {
		UserProfileResponse response = new UserProfileResponse();
		Integer userId = request.getUserId();
		// Fetch user details
		UserDto user = userDao.fetchUser(userId);
		// Fetch user checkin count
		Integer userCheckinCount = checkinDao.getUserCheckinCount(userId);
		response.setUser(mapper.map(user, userCheckinCount));
		response.getUser().setIsFollow(Boolean.TRUE);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SearchFriendToTagResponse searchFriendsToTag(SearchFriendRequest request) throws BusinessException {
		SearchFriendToTagResponse response = new SearchFriendToTagResponse(); 
		List<UserDto> users = userDao.fetchUsersBySearchString(request.getSearchString());
		if (users != null) {
			response.setUsers(mapper.map(users));
		}
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SearchFriendResponse searchFriends(SearchFriendRequest request) throws BusinessException {
		SearchFriendResponse response = new SearchFriendResponse();
		// FIXME : adding temporary logic to return search friend response
		List<UserDto> users = userDao.fetchUsersBySearchString(request.getSearchString());
		if (users != null) {
			response.setPeople(mapper.map(users));
			if (validateIfLoggedInUser()) {
				response.setFriends(mapper.map(users));
			}
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FriendResponse getMyFriends(GetMyFriendsRequest request) throws BusinessException {
		FriendResponse response = new FriendResponse();
		//FIXME : add temporary logic to return my friends
		List<UserDto> users = userFollowerDao.fetchMyFriendsByPage(request.getPage(), getCurrentUserId());
		if (users != null) {
			response.setFriends(mapper.map(users));
		}
		return response;
	}
	
	/**
	 * check if user is logged in or not
	 * FIXME : Move such logics to a common place
	 */
	private boolean validateIfLoggedInUser() {
		RoleType role = RoleType.getRole(jwtHelper.getUserName());
		if (role == RoleType.GUEST) {
			return false;
		}
		return true;
	}

	/**
	 * check if user is logged in or not
	 * FIXME : Move such logics to a common place
	 */
	private void authorizeUser() throws BusinessException {
		List<String> roles = jwtHelper.getRoles();
		if (roles == null || roles.isEmpty()) {
			throw new BusinessException(UserErrorCodeType.LOGIN_REQUIRED);
		}

		for (String role : roles) {
			if (RoleType.GUEST == RoleType.getRole(role)) {
				throw new BusinessException(UserErrorCodeType.LOGIN_REQUIRED);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SaveRegistrationIdResponse saveRegistrationId(SaveRegistrationIdRequest request) throws BusinessException {
		SaveRegistrationIdResponse response = new SaveRegistrationIdResponse();
		Integer userId = Integer.valueOf(jwtHelper.getUserName());
		UserDto user = userDao.fetchUser(userId);
		if (user == null) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_FOUND);
		}
		
		userDao.saveRegistrationIdForUser(userId, request.getRegistrationId());
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public FollowResponse follow(FollowRequest request) throws BusinessException {
		FollowResponse response = new FollowResponse();
		//Validate whether the user exists
		UserDto user = userDao.fetchUser(request.getUserId());
		if (user == null) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_FOUND);
		}
		
		//Validate if same user is trying to follow himself!!
		if (request.getUserId() == getCurrentUserId()) {
			throw new BusinessException(UserErrorCodeType.USER_CANNOT_FOLLOW_HIMSELF);
		}
		
		//Validate whether the user is already following or not
		if (userFollowerDao.isAlreadyFollowing(request.getUserId(), getCurrentUserId())) {
			throw new BusinessException(UserErrorCodeType.USER_ALREADY_FOLLOWING);
		}
		
		userFollowerDao.follow(request.getUserId(), getCurrentUserId());
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UnFollowResponse unFollow(UnFollowRequest request) throws BusinessException {
		UnFollowResponse response = new UnFollowResponse();
		//Validate whether the user is already following or not
		if (!userFollowerDao.isAlreadyFollowing(request.getUserId(), getCurrentUserId())) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_FOLLOWING);
		}
		
		userFollowerDao.unFollow(request.getUserId(), getCurrentUserId());
		return response;
	}
	
	private Integer getCurrentUserId() {
		return Integer.valueOf(jwtHelper.getUserName());
	}
}
