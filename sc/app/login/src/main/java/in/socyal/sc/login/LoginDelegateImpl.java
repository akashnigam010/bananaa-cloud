package in.socyal.sc.login;

import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import in.socyal.sc.api.login.dto.BusinessLoginUserDto;
import in.socyal.sc.api.login.request.BusinessLoginRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.BusinessLoginResponse;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.merchant.business.dto.MerchantLoginDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.facebook.OAuth2FbHelper;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.login.type.LoginErrorCodeType;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.MerchantDao;
import in.socyal.sc.persistence.MerchantLoginDao;
import in.socyal.sc.persistence.UserDao;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	private static final Logger LOG = Logger.getLogger(LoginDelegateImpl.class);
	private static final String BANANAA_SUPPORT = "bananaa.contact.support";
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	
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

	@Override
	public LoginResponse skipLogin() {
		LoginResponse response = new LoginResponse();
		// Sets JWT access token
		response.setAccessToken(JwtHelper.createJsonWebTokenForGuest());
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessLoginResponse businessLogin(BusinessLoginRequest request) {
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
}
