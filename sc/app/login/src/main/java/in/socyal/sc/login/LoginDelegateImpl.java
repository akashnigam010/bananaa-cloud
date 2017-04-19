package in.socyal.sc.login;

import java.io.IOException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.firebase.FirebaseUser;
import in.socyal.sc.api.firebase.Uid;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.aws.S3Helper;
import in.socyal.sc.helper.facebook.OAuth2FbHelper;
import in.socyal.sc.helper.firebase.FirebaseAuthHelper;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.UserDao;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	private static final Logger LOG = Logger.getLogger(LoginDelegateImpl.class);

	@Autowired
	UserDao userDao;
	@Autowired
	LoginMapper mapper;
	@Autowired
	OAuth2FbHelper fbHelper;
	@Autowired
	MerchantDao merchantDao;
	@Autowired
	FirebaseAuthHelper firebaseHelper;
	@Autowired
	S3Helper s3Helper;

	@Override
	public LoginResponse skipLogin() throws BusinessException {
		LoginResponse response = new LoginResponse();
		// Sets JWT access token
		response.setAccessToken(JwtHelper.createJsonWebTokenForGuest());
		response.setUser(mapper.mapGuestUser());
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public LoginResponse firebaseLogin(IdTokenRequest request) throws BusinessException {
		LoginResponse loginResponse = new LoginResponse();
		Uid uid = firebaseHelper.getUid(request.getIdToken());
		if (!uid.isStatus()) {
			LOG.debug("Invalid id token passed while logging in");
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		UserDto user = userDao.getUserByUid(uid.getUid());
		if (user == null) {
			FirebaseUser firebaseUser = firebaseHelper.getUserDetails(uid.getUid());
			if (!firebaseUser.isStatus()) {
				LOG.debug("Error occurred while fetching user details from firebase");
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
			UserDto newUser = mapper.mapFirebaseUser(firebaseUser);
			try {
				newUser.setImageUrl(s3Helper.saveUserImage(newUser.getImageUrl(), newUser.getNameId()));
			} catch (IOException e) {
				LOG.error("Error occurred while saving user image to S3 : " + e.getMessage() + ", USER : "
						+ newUser.toString());
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
			user = userDao.saveUser(newUser);
		}
		loginResponse.setUser(mapper.mapToLoginUserDto(user));
		return loginResponse;
	}
}
