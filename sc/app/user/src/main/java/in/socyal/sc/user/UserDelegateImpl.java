package in.socyal.sc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.type.error.UserErrorCodeType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.user.request.SavePreferencesRequest;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.UserDao;

@Service
public class UserDelegateImpl implements UserDelegate {

	@Autowired
	UserDao userDao;
	
	@Autowired
	JwtTokenHelper jwtHelper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public UserDetailsResponse getUserDetails(String userNameId) throws BusinessException {
		UserDto userDto = userDao.getUserByNameId(userNameId);
		UserDetailsResponse response = new UserDetailsResponse();
		response.setUser(userDto);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void saveUserPreferences(SavePreferencesRequest request) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		userDao.saveUserPreferences(request, jwtHelper.getUserId());		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Integer getVegnonvegPreference() throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		return userDao.getVegnonvegPreference(jwtHelper.getUserId());
	}
}
