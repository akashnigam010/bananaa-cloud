package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.NotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.notification.NotificationDelegate;

@RestController
@RequestMapping(value = "/socyal/notification")
public class NotificationService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	NotificationDelegate notificationDelegate;

	@RequestMapping(value = "/sendTestDataMessage", method = RequestMethod.POST, headers = "Accept=application/json")
	public SendTestNotificationResponse sendTestDataMessage(@RequestBody NotificationRequest request) {
		JsonHelper.logRequest(request, NotificationService.class, "/login/sendTestDataMessage");
		SendTestNotificationResponse response = new SendTestNotificationResponse();
		try {
			response = notificationDelegate.sendDataNotification(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
