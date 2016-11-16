package in.socyal.sc.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.bo.personnel.request.UserSignOnRequest;
import in.socyal.sc.api.bo.personnel.response.UserSignOnResponse;
import in.socyal.sc.core.validation.LoginValidator;
import in.socyal.sc.dbhelper.LoginSampleDao;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.LoginDelegate;

@RestController
@RequestMapping(value = "/login")
public class LoginService {
	private boolean isPinSignOn;
	@Autowired
	LoginDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	LoginValidator validator;
	@Autowired
	LoginSampleDao dao;

	@RequestMapping(value = "/signOn", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserSignOnResponse signOn(@RequestBody UserSignOnRequest request) {
		UserSignOnResponse response = new UserSignOnResponse();
		isPinSignOn = false;
		try {
			validator.validateLoginService(request, isPinSignOn);
			response = delegate.login(request, isPinSignOn);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/pinSignOn", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserSignOnResponse pinSignOn(@RequestBody UserSignOnRequest request) {
		UserSignOnResponse response = new UserSignOnResponse();
		isPinSignOn = true;
		try {
			validator.validateLoginService(request, isPinSignOn);
			response = delegate.login(request, isPinSignOn);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	
	@RequestMapping(value = "/sample", method = RequestMethod.POST, headers = "Accept=application/json")
	public void sample(@RequestBody String name) {
		dao.save(name);
	}
}
