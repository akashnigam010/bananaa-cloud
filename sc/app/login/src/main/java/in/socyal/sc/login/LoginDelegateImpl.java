package in.socyal.sc.login;

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
			user = userDao.saveUser(newUser);
		}
		loginResponse.setUser(mapper.mapToLoginUserDto(user));
		return loginResponse;
	}

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	// BusinessException.class)
	// public LoginResponse fbLogin(LoginRequest request) throws
	// BusinessException {
	// LoginResponse response = new LoginResponse();
	// try {
	// User fbUser = fbHelper.getFbUser(request.getFbAccessToken());
	// if (!fbUser.getId().equals(request.getFbId())) {
	// throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
	// }
	// // Save or update user
	// UserDto userDetails = userDao.saveOrUpdate(fbUser,
	// request.getFbAccessToken());
	// // Sets JWT access token
	// response.setAccessToken(JwtHelper.createJsonWebTokenForUser(String.valueOf(userDetails.getId())));
	// Integer userCheckinCount =
	// checkinDao.getUserCheckinCount(userDetails.getId());
	// response.setUser(mapper.mapFbUserToUserDto(userDetails,
	// userCheckinCount));
	// } catch (FacebookOAuthException e) {
	// LOG.error("Error while fetching user details from FB " +
	// e.getErrorMessage());
	// throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
	// }
	// return response;
	// }
	//
	// @Override
	// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	// BusinessException.class)
	// public BusinessLoginResponse businessLogin(BusinessLoginRequest request)
	// throws BusinessException {
	// BusinessLoginResponse response = new BusinessLoginResponse();
	// MerchantLoginDto merchantLoginDto =
	// merchantLoginDao.validateBusinessUser(request.getUsername(),
	// request.getPassword());
	// if (merchantLoginDto == null) {
	// throw new
	// BusinessException(LoginErrorCodeType.BUSINESS_CREDENTIALS_INVALID);
	// }
	// response.setAccessToken(JwtHelper.createJsonWebTokenForMerchant(String.valueOf(merchantLoginDto.getDeviceId()),
	// String.valueOf(merchantLoginDto.getMerchant().getId())));
	//
	// BusinessLoginUserDto loggedInUser = new BusinessLoginUserDto();
	// MerchantDto merchant = merchantLoginDto.getMerchant();
	// loggedInUser.setId(merchant.getId());
	// loggedInUser.setName(merchant.getName());
	// loggedInUser.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
	// response.setUser(loggedInUser);
	// response.setSupportNumber(resource.getString(BANANAA_SUPPORT));
	// return response;
	// }
}
