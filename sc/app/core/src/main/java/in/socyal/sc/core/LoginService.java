package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.personnel.dto.UserAccessTokenDto;
import in.socyal.sc.api.personnel.request.UserSignOnRequest;
import in.socyal.sc.api.personnel.response.UserSignOnResponse;
import in.socyal.sc.core.mapper.LoginServiceMapper;
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
	@Autowired
	LoginServiceMapper mapper;
//	@Autowired
//	LoginSampleDao dao;

	@RequestMapping(value = "/thirdPartyLogin", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserSignOnResponse thirdPartyLogin(@RequestParam String code) {
		UserSignOnResponse response = new UserSignOnResponse();
		try {
			System.out.println(code);
			validator.validateThirdPartyLoginRequest(code);
			delegate.thirdPartyLogin(code);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/signOn", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserSignOnResponse signOn(@RequestBody UserSignOnRequest request) {
		UserSignOnResponse response = new UserSignOnResponse();
		try {
			validator.validateSignOnLoginRequest(request);
			UserAccessTokenDto accessToken = delegate.login(request);
			//dao.save(request.getUserName()+request.getPassword());
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
