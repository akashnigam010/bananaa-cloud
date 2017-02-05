package in.socyal.sc.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;
import in.socyal.sc.core.validation.NotificationValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.notification.NotificationDelegate;

@RestController
@RequestMapping(value = "/socyal/notification")
public class NotificationService {
	private static final Logger LOG = Logger.getLogger(NotificationService.class);
	@Autowired ResponseHelper helper;
	@Autowired NotificationDelegate notificationDelegate;
	@Autowired NotificationValidator validator;

	@RequestMapping(value = "/sendTestNotification", method = RequestMethod.POST, headers = "Accept=application/json")
	public SendTestNotificationResponse sendTestNotification(@RequestBody SendTestNotificationRequest request) {
		JsonHelper.logRequest(request, NotificationService.class, "/login/sendNotification");
		SendTestNotificationResponse response = new SendTestNotificationResponse();
		try {
			validator.validateSendNotificationRequest(request);
			response = notificationDelegate.sendNotificationWithData(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/sendTestDataMessage", method = RequestMethod.POST, headers = "Accept=application/json")
	public SendTestNotificationResponse sendTestDataMessage(@RequestBody SendTestNotificationRequest request) {
		JsonHelper.logRequest(request, NotificationService.class, "/login/sendTestDataMessage");
		SendTestNotificationResponse response = new SendTestNotificationResponse();
		try {
			validator.validateSendNotificationRequest(request);
			response = notificationDelegate.sendDataMessage(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
