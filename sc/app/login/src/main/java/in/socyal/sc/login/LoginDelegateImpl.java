package in.socyal.sc.login;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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
	
	public static void main(String[] args) throws BusinessException {
		LoginDelegateImpl impl = new LoginDelegateImpl();
		IdTokenRequest idTokenRequest = new IdTokenRequest();
		idTokenRequest.setIdToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjJiOTkyYjgyMGQzZWZlNTA0ODAzZDMyZDE4NmY2YmY0NjRiYWI1MGEifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdGVzdHNpZ25pbi0xNjA2MTEiLCJuYW1lIjoiQWthc2ggTmlnYW0iLCJwaWN0dXJlIjoiaHR0cHM6Ly9zY29udGVudC54eC5mYmNkbi5uZXQvdi90MS4wLTEvczEwMHgxMDAvMTY5OTg4MzVfMTI1NDIyMTk3NDYzMzE4M184NzcwMzMyODMxMjgzMzc2MDI1X24uanBnP29oPTIzZTQ3ZTQ2NTg4ODJiY2I2YzJjMDIxMTdlZGRmYjlmJm9lPTU5Nzg4MUQzIiwiYXVkIjoidGVzdHNpZ25pbi0xNjA2MTEiLCJhdXRoX3RpbWUiOjE0OTMwNTIxNDgsInVzZXJfaWQiOiJ2TEFlVVdXM2JoTVRpZEFuUmJTNklnUFJZUHkxIiwic3ViIjoidkxBZVVXVzNiaE1UaWRBblJiUzZJZ1BSWVB5MSIsImlhdCI6MTQ5MzA1MjE0OSwiZXhwIjoxNDkzMDU1NzQ5LCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImZhY2Vib29rLmNvbSI6WyIxMTU4MjE4NTkwOTAwMTg5Il19LCJzaWduX2luX3Byb3ZpZGVyIjoiZmFjZWJvb2suY29tIn19.HX35n2IZhwpsP3wYBh73wdwf-305Gw1od2PXBlpMtFp23V5gZX5aCcxuoCoQ4bkJoUnvuC7yZneMFYAjFc-Ms66X1re7s83iSR8UppPzDYBYgftQSPovuWati11sAm9uRkIjIiYQCMX56Cjk-dTMgQFXyo-491RPUJqa-d02G1dzaIvB9uE-Z8HFUEqQGbvMv6aPIdygQjXZEBQC3rKSPRTFsPrT4wgB1foo5qmJeymbtMcTVIzwQiqYyBSihXEvECzJCFvOWSJ7L3LO_39gVGhQsBi7HHAamlK3_4LQzadtPW1ONEOozCtC9vYWv5JW_eCncrW_oc1lyRRYhh5IPg");
		impl.firebaseLogin(idTokenRequest);
	}
}
