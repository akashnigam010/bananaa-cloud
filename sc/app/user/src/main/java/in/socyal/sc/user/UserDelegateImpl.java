package in.socyal.sc.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.api.user.response.SearchFriendResponse;
import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.UserDao;
import in.socyal.sc.user.mapper.UserMapper;
import in.socyal.sc.user.type.UserErrorCodeType;

@Service
public class UserDelegateImpl implements UserDelegate {
	@Autowired JwtTokenHelper jwtHelper;
	@Autowired UserDao userDao;
	@Autowired CheckinDao checkinDao;
	@Autowired UserMapper mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserProfileResponse getProfile() throws BusinessException {
		authorizeUser();
		UserProfileResponse response = new UserProfileResponse();
		Integer userId = Integer.valueOf(jwtHelper.getUserName());
		//Fetch user details
		UserDto user = userDao.fetchUser(userId);
		//Fetch user checkin count
		Integer userCheckinCount = checkinDao.getUserCheckinCount(userId);
		mapper.map(user, response, userCheckinCount);
		return response;
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SearchFriendResponse searchFriends(SearchFriendRequest request) throws BusinessException {
		SearchFriendResponse response = new SearchFriendResponse(); 
		List<UserDto> users = userDao.fetchUsers(request.getSearchString());
		if (users != null) {
			mapper.map(users, response);
		}
		return response;
	}
	
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
}
