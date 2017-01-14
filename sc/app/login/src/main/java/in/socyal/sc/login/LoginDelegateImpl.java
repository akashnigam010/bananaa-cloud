package in.socyal.sc.login;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.login.type.LoginErrorCodeType;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.UserDao;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	private static final Logger LOG = Logger.getLogger(LoginDelegateImpl.class);
	@Autowired
	UserDao userDao;
	@Autowired
	CheckinDao checkinDao;
	@Autowired
	LoginMapper mapper;
	@Autowired
	OAuth2FbHelper fbHelper;

	@Override
	public LoginResponse skipLogin() {
		LoginResponse response = new LoginResponse();
		// Sets JWT access token
		response.setAccessToken(JwtHelper.createJsonWebToken(RoleType.GUEST.getRole(), RoleType.GUEST.getRole(), 1L));
		response.setUser(mapper.mapGuestUser());
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LoginResponse fbLogin(LoginRequest request) throws BusinessException {
		LoginResponse response = new LoginResponse();
		try {
			User fbUser = fbHelper.getFbUser(request.getFbAccessToken());
			if (!fbUser.getId().equals(request.getFbId())) {
				throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
			}
			// Save or update user
			UserDto userDetails = userDao.saveOrUpdate(fbUser, request.getFbAccessToken());
			// Sets JWT access token
			response.setAccessToken(
					JwtHelper.createJsonWebToken(userDetails.getId().toString(), RoleType.USER.getRole(), 365L));
			Integer userCheckinCount = checkinDao.getUserCheckinCount(userDetails.getId());
			response.setUser(mapper.mapFbUserToUserDto(userDetails, userCheckinCount));
		} catch (FacebookOAuthException e) {
			LOG.error("Error while fetching user details from FB " + e.getErrorMessage());
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}
		return response;
	}
}
