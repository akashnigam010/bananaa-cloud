package in.socyal.sc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.UserErrorCodeType;
import in.socyal.sc.api.user.dto.Profile;
import in.socyal.sc.api.user.dto.UserDto;
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
	public Integer getVegnonvegPreference() throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		return userDao.getVegnonvegPreference(jwtHelper.getUserId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void saveVegnonvegPreference(Integer vegnonvegId) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		userDao.saveVegnonvegPreference(vegnonvegId, jwtHelper.getUserId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateTagPreference(Integer id, TagType type, boolean isRemove) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		userDao.updateTagPreference(id, jwtHelper.getUserId(), type, isRemove);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Profile getUserProfile(IdRequest request) throws BusinessException {
		Profile profile = userDao.getUserProfile(request.getId());
		profile.setVegnonvegId(userDao.getVegnonvegPreference(request.getId()));		
		return profile;
	}
}
