package in.socyal.sc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.MLoginRequest;
import in.socyal.sc.api.login.request.SetLocationRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.cache.CityCache;
import in.socyal.sc.cache.LocalityCache;
import in.socyal.sc.core.validation.LoginValidator;
import in.socyal.sc.helper.LocalityCookieHelper;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.login.LoginDelegate;

@RestController
@RequestMapping(value = "/socyal/login")
public class LoginService {
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
	@Autowired
	LocalityCache localityCache;
	@Autowired
	CityCache cityCache;
	@Autowired
	LocalityCookieHelper cookieHelper;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse login(@RequestBody LoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			validator.validateLoginRequest(request);
			response = delegate.federatedLogin(request);
			addLoginCookie(response);
			cookieHelper.addDefaultCityCookie(httpResponse);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/mLogin", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse mLogin(@RequestBody MLoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			validator.validateLoginRequest(request);
			response = delegate.manualLogin(request);
			addLoginCookie(response);
			cookieHelper.addDefaultCityCookie(httpResponse);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse register(@RequestBody MLoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			validator.validateRegisterRequest(request);
			response = delegate.register(request);
			addLoginCookie(response);
			cookieHelper.addDefaultCityCookie(httpResponse);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse resetPassword(@RequestBody MLoginRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateResetPasswordRequest(request);
			response = delegate.resetPassword(request);
			cookieHelper.addDefaultCityCookie(httpResponse);
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

	@RequestMapping(value = "/setLocation", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse setLocation(@RequestBody SetLocationRequest request) {
		addLocationCookie(request.getLocalityId());
		return helper.success(new StatusResponse());
	}

	private void addLoginCookie(LoginResponse response) throws BusinessException {
		Cookie loginCookie = new Cookie("blc",
				JwtHelper.createJsonWebTokenForUser(response.getUser().getId().toString(),
						response.getUser().getFirstName(), response.getUser().getNameId()));
		loginCookie.setPath("/");
		httpResponse.addCookie(loginCookie);

	}

	private void addLocationCookie(String nameId) {
		Cookie localityCookie = null;
		LocalityDto locality = localityCache.getLocality(nameId);
		if (locality != null) {
			localityCookie = new Cookie("loc", locality.getNameId());
		} else {
			CityDto city = cityCache.getCity(nameId);
			localityCookie = new Cookie("loc", city.getNameId());
		}
		localityCookie.setPath("/");
		httpResponse.addCookie(localityCookie);
	}

	private void removeLoginCookie() {
		Cookie loginCookie = new Cookie("blc", "");
		loginCookie.setPath("/");
		httpResponse.addCookie(loginCookie);

	}
}
