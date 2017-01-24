package in.socyal.sc.core;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;
import in.socyal.sc.core.validation.LoginValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.LoginDelegate;
import in.socyal.sc.login.NotificationDelegate;

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
	NotificationDelegate notificationDelegate;

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
	
	@RequestMapping(value = "/fbLogin", method = RequestMethod.POST, headers = "Accept=application/json")
	public LoginResponse fbLogin(@RequestBody LoginRequest request) {
		JsonHelper.logRequest(request, LoginService.class, "/login/fbLogin");
		LoginResponse response = new LoginResponse();
		try {
			validator.validateFbLoginRequest(request);
			response = delegate.fbLogin(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/sendTestNotification", method = RequestMethod.POST, headers = "Accept=application/json")
	public SendTestNotificationResponse sendTestNotification(@RequestBody SendTestNotificationRequest request) {
		JsonHelper.logRequest(request, LoginService.class, "/login/sendNotification");
		SendTestNotificationResponse response = new SendTestNotificationResponse();
		try {
			validator.validateSendNotificationRequest(request);
			response = notificationDelegate.sendTestNotification(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
