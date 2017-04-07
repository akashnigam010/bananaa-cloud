package in.socyal.sc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.request.BusinessLoginRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.BusinessLoginResponse;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.login.response.LoginUserDto;
import in.socyal.sc.core.validation.LoginValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.login.LoginDelegate;

@RestController
@RequestMapping(value = "/socyal/login")
public class LoginService {
	private static final Logger LOG = Logger.getLogger(LoginService.class);
	@Autowired
	LoginDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	LoginValidator validator;
	@Autowired
	HttpServletResponse httpResponse;
	@Autowired
	HttpServletRequest httpRequest;

	@RequestMapping(value = "/skipLogin", method = RequestMethod.GET, headers = "Accept=application/json")
	public LoginResponse skipLogin() {
		LoginResponse response = new LoginResponse();
		try {
			LOG.info("Skip login request");
			response = delegate.skipLogin();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse login(@RequestBody IdTokenRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			response = delegate.firebaseLogin(request);
			addLoginCookie(response);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	private void addLoginCookie(LoginResponse response) throws BusinessException {
		Cookie cookie = new Cookie("blc", JwtHelper.createJsonWebTokenForUser(response.getUser().getId().toString()));
		cookie.setPath("/");
		httpResponse.addCookie(cookie);
	}

	// @RequestMapping(value = "/businessLogin", method = RequestMethod.POST,
	// headers = "Accept=application/json")
	// public BusinessLoginResponse businessLogin(@RequestBody
	// BusinessLoginRequest request) {
	// BusinessLoginResponse response = new BusinessLoginResponse();
	// try {
	// LOG.info("Business login request");
	// validator.validateBusinessLoginRequest(request);
	// response = delegate.businessLogin(request);
	// return helper.success(response);
	// } catch (BusinessException e) {
	// return helper.failure(response, e);
	// }
	// }
	//
	// @RequestMapping(value = "/fbLogin", method = RequestMethod.POST, headers
	// = "Accept=application/json")
	// public LoginResponse fbLogin(@RequestBody LoginRequest request) {
	// JsonHelper.logRequest(request, LoginService.class, "/login/fbLogin");
	// LoginResponse response = new LoginResponse();
	// try {
	// validator.validateFbLoginRequest(request);
	// response = delegate.fbLogin(request);
	// return helper.success(response);
	// } catch (BusinessException e) {
	// return helper.failure(response, e);
	// }
	// }
}
