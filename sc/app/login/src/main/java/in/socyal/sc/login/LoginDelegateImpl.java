package in.socyal.sc.login;

import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.dto.BusinessLoginUserDto;
import in.socyal.sc.api.login.request.BusinessLoginRequest;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.BusinessLoginResponse;
import in.socyal.sc.api.login.response.FirebaseLoginResponse;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.merchant.business.dto.MerchantLoginDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.type.error.LoginErrorCodeType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.facebook.OAuth2FbHelper;
import in.socyal.sc.helper.firebase.FirebaseAuthHelper;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.MerchantLoginDao;
import in.socyal.sc.persistence.UserDao;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	private static final Logger LOG = Logger.getLogger(LoginDelegateImpl.class);
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String BANANAA_SUPPORT = "bananaa.contact.support";
	private static final String BANANAA_ABOUT_US_LINK = "bananaa.aboutus.link";
	private static final String BANANAA_RATE_US_LINK = "bananaa.rateus.link";
	private static final String BANANAA_CONTACT_US_MAIL = "bananaa.contactus.mail";
	
	
	@Autowired
	UserDao userDao;
	@Autowired
	CheckinDao checkinDao;
	@Autowired
	LoginMapper mapper;
	@Autowired
	OAuth2FbHelper fbHelper;
	@Autowired
	MerchantLoginDao merchantLoginDao;
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
			response.setAccessToken(JwtHelper.createJsonWebTokenForUser(String.valueOf(userDetails.getId())));
			Integer userCheckinCount = checkinDao.getUserCheckinCount(userDetails.getId());
			response.setUser(mapper.mapFbUserToUserDto(userDetails, userCheckinCount));
		} catch (FacebookOAuthException e) {
			LOG.error("Error while fetching user details from FB " + e.getErrorMessage());
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public BusinessLoginResponse businessLogin(BusinessLoginRequest request) throws BusinessException {
		BusinessLoginResponse response = new BusinessLoginResponse();
		MerchantLoginDto merchantLoginDto = merchantLoginDao.validateBusinessUser(request.getUsername(), request.getPassword());
		if (merchantLoginDto == null) {
			throw new BusinessException(LoginErrorCodeType.BUSINESS_CREDENTIALS_INVALID);
		}
		response.setAccessToken(JwtHelper.createJsonWebTokenForMerchant(String.valueOf(merchantLoginDto.getDeviceId()),
																		String.valueOf(merchantLoginDto.getMerchant().getId())));
		
		BusinessLoginUserDto loggedInUser = new BusinessLoginUserDto();
		MerchantDto merchant = merchantLoginDto.getMerchant();
		loggedInUser.setId(merchant.getId());
		loggedInUser.setName(merchant.getName());
		loggedInUser.setShortAddress(merchant.getAddress().getLocality().getShortAddress());
		response.setUser(loggedInUser);
		response.setSupportNumber(resource.getString(BANANAA_SUPPORT));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public FirebaseLoginResponse firebaseLogin(IdTokenRequest request) throws BusinessException {
		firebaseHelper.getUid(request.getIdToken());
		return null;
	}
}
