package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.LoginResponse;
import in.socyal.sc.core.validation.LoginValidator;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.LoginDelegate;

@RestController
@RequestMapping(value = "/login")
public class LoginService {
	@Autowired
	LoginDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	LoginValidator validator;

	@RequestMapping(value = "/skipLogin", method = RequestMethod.GET, headers = "Accept=application/json")
	public LoginResponse skipLogin() {
		LoginResponse response = new LoginResponse();
		try {
			response = delegate.skipLogin();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/fbLogin", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse fbLogin(@RequestBody LoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			validator.validateFbLoginRequest(request);
			response = delegate.fbLogin(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/fbLoginWithCode", method = RequestMethod.GET, headers = "Accept=application/json")
	public LoginResponse fbLoginWithCode(@RequestParam String code) {
		LoginResponse response = new LoginResponse();
		try {
			response = delegate.fbLoginWithCode(code);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
