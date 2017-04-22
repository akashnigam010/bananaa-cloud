package in.socyal.sc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.type.CityType;
import in.socyal.sc.core.validation.LoginValidator;
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
			addLocationCookie();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, headers = "Accept=application/json")
	public LoginResponse logout() {
		LoginResponse response = new LoginResponse();
		new SecurityContextLogoutHandler().logout(httpRequest, null, null);
		removeLoginCookie();
		return helper.success(response);
	}

	private void addLoginCookie(LoginResponse response) throws BusinessException {
		Cookie loginCookie = new Cookie("blc",
				JwtHelper.createJsonWebTokenForUser(response.getUser().getId().toString(),
						response.getUser().getFirstName(), response.getUser().getNameId()));
		loginCookie.setPath("/");
		httpResponse.addCookie(loginCookie);

	}

	private void addLocationCookie() {
		Cookie cityCookie = new Cookie("city", CityType.HYDERABAD.getName());
		cityCookie.setPath("/");
		httpResponse.addCookie(cityCookie);
	}

	private void removeLoginCookie() {
		Cookie loginCookie = new Cookie("blc", "");
		loginCookie.setPath("/");
		httpResponse.addCookie(loginCookie);

	}
}
