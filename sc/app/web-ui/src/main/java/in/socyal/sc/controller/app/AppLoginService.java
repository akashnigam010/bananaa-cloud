package in.socyal.sc.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.core.validation.LoginValidator;
import in.socyal.sc.helper.security.jwt.JwtHelper;
import in.socyal.sc.login.LoginDelegate;

@RestController
@RequestMapping(value = "/bna/login")
public class AppLoginService {
	@Autowired
	LoginDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	LoginValidator validator;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse login(@RequestBody LoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			validator.validateLoginRequest(request);
			response = delegate.federatedLogin(request);
			response.setAccessToken(getAccessToken(response));
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	private String getAccessToken(LoginResponse response) throws BusinessException {
		return JwtHelper.createJsonWebTokenForUser(response.getUser().getId().toString(),
				response.getUser().getFirstName(), response.getUser().getNameId());
	}
}
