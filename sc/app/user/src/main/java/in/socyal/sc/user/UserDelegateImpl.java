package in.socyal.sc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.UserDao;
import in.socyal.sc.user.mapper.UserMapper;

@Service
public class UserDelegateImpl implements UserDelegate {
	@Autowired JwtTokenHelper jwtHelper;
	@Autowired UserDao userDao;
	@Autowired UserMapper mapper;

	@Override
	public UserProfileResponse getProfile() throws BusinessException {
		UserProfileResponse response = new UserProfileResponse();
		String userId = jwtHelper.getUserName();
		UserDto user = userDao.fetchUser(Integer.valueOf(userId));
		mapper.map(user, response);
		return response;
	}
}
