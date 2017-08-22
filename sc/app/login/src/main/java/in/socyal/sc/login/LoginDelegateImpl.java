package in.socyal.sc.login;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.firebase.FirebaseUser;
import in.socyal.sc.api.firebase.Uid;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.ClientType;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.MLoginRequest;
import in.socyal.sc.api.login.response.FederatedUser;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.LoginErrorCodeType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.aws.S3Helper;
import in.socyal.sc.helper.facebook.OAuth2FbHelper;
import in.socyal.sc.helper.firebase.FirebaseAuthHelper;
import in.socyal.sc.helper.google.OAuth2GoogleHelper;
import in.socyal.sc.helper.mail.MailSender;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.UserDao;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	private static final Logger LOG = Logger.getLogger(LoginDelegateImpl.class);
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String DEFAULT_PHOTO = "default.user.photo.url";

	@Autowired
	UserDao userDao;
	@Autowired
	LoginMapper mapper;
	@Autowired
	OAuth2FbHelper fbHelper;
	@Autowired
	OAuth2GoogleHelper googleHelper;
	@Autowired
	MerchantDao merchantDao;
	@Autowired
	FirebaseAuthHelper firebaseHelper;
	@Autowired
	S3Helper s3Helper;
	@Autowired
	MailSender mailSender;

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
			UserDto userByEmail = null;
			if (StringUtils.isNotBlank(firebaseUser.getUser().getEmail())) {
				userByEmail = userDao.getUserByEmail(firebaseUser.getUser().getEmail());
			}
			if (userByEmail == null) {
				// new user
				UserDto newUser = mapper.mapFirebaseUser(firebaseUser);
				try {
					newUser.setImageUrl(s3Helper.saveUserImage(newUser.getImageUrl(), newUser.getNameId()));
				} catch (IOException e) {
					LOG.error("Error occurred while saving user image to S3 : " + e.getMessage() + ", USER : "
							+ newUser.toString());
					throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
				}
				user = userDao.saveUser(newUser);
			} else {
				// user logging in with different provider
				user = userByEmail;
			}
		}
		loginResponse.setUser(mapper.mapToLoginUserDto(user));
		return loginResponse;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public LoginResponse federatedLogin(LoginRequest request) throws BusinessException {
		LoginResponse loginResponse = new LoginResponse();
		FederatedUser federatedUser = getFederatedUser(request);
		UserDto user = null;
		if (StringUtils.isNotBlank(federatedUser.getEmail())) {
			// email id passed
			user = userDao.getUserByEmail(federatedUser.getEmail());
			if (user == null) {
				// new user with email
				user = saveNewUser(federatedUser, null);
			}
		} else {
			// email id not passed
			user = userDao.getUserByUid(federatedUser.getId());
			if (user == null) {
				// user did not login with this client earlier, save new user
				// without email
				user = saveNewUser(federatedUser, null);
			}
		}

		loginResponse.setUser(mapper.mapToLoginUserDto(user));
		return loginResponse;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public LoginResponse manualLogin(MLoginRequest request) throws BusinessException {
		LoginResponse loginResponse = new LoginResponse();
		UserDto user = userDao.getUserByEmailWithPassword(request.getEmail());
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		} else if (StringUtils.isBlank(user.getPassword())) {
			throw new BusinessException(LoginErrorCodeType.REGISTERED_VIA_FB_OR_GOOGLE);
		} else if (!request.getPassword().equalsIgnoreCase(user.getPassword())) {
			throw new BusinessException(LoginErrorCodeType.INCORRECT_PASSWORD);
		}
		loginResponse.setUser(mapper.mapToLoginUserDto(user));
		return loginResponse;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public LoginResponse register(MLoginRequest request) throws BusinessException {
		LoginResponse loginResponse = new LoginResponse();
		UserDto user = userDao.getUserByEmail(request.getEmail());
		if (user != null) {
			throw new BusinessException(LoginErrorCodeType.EMAIL_ALREADY_REGISTERED);
		} else {
			FederatedUser federatedUser = new FederatedUser();
			federatedUser.setName(request.getName());
			federatedUser.setEmail(request.getEmail());
			federatedUser.setPhotoUrl(resource.getString(DEFAULT_PHOTO));
			federatedUser.setFederatedLogin(false);
			user = saveNewUser(federatedUser, request.getPassword());
			loginResponse.setUser(mapper.mapToLoginUserDto(user));
			return loginResponse;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public StatusResponse resetPassword(MLoginRequest request) throws BusinessException {
		StatusResponse statusResponse = new StatusResponse();
		UserDto user = userDao.getUserByEmailWithPassword(request.getEmail());
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		} else if (StringUtils.isBlank(user.getPassword())) {
			throw new BusinessException(LoginErrorCodeType.REGISTERED_VIA_FB_OR_GOOGLE);
		} else {
			// email the password to user
			request.setName(user.getFirstName());
			request.setPassword(user.getPassword());
			mailSender.sendPasswordMail(request);
			return statusResponse;
		}
	}
	
	private UserDto saveNewUser(FederatedUser federatedUser, String password) throws BusinessException {
		UserDto newUser = mapper.mapFederatedUser(federatedUser, password);
		try {
			if (federatedUser.isFederatedLogin()) {
				newUser.setImageUrl(s3Helper.saveUserImage(newUser.getImageUrl(), newUser.getNameId()));
			}			
		} catch (IOException e) {
			LOG.error("Error occurred while saving user image to S3 : " + e.getMessage() + ", USER : "
					+ newUser.toString());
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		newUser = userDao.saveUser(newUser);
		return newUser;
	}

	private FederatedUser getFederatedUser(LoginRequest request) throws BusinessException {
		FederatedUser federatedUser = null;
		if (request.getClient() == ClientType.GOOGLE) {
			federatedUser = mapper.mapGoogleUser(googleHelper.getGoogleUser(request.getAccessToken()));
			// googleHelper.revokeToken(request.getAccessToken());
		} else if (request.getClient() == ClientType.FACEBOOK) {
			federatedUser = mapper.mapFacebookUser(fbHelper.getFbUser(request.getAccessToken()));
		} else {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		return federatedUser;
	}
}
