package in.socyal.sc.login;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.jboss.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.login.request.Data;
import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.notification.FirebaseResponse;
import in.socyal.sc.helper.notification.NotificationsService;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Service
public class NotificationDelegateImpl implements NotificationDelegate {
	private static final Logger log = Logger.getLogger(NotificationDelegateImpl.class);

	@Autowired
	NotificationsService pushNotificationsService;

	@Override
	public SendTestNotificationResponse sendTestNotification(SendTestNotificationRequest request)
			throws BusinessException {

		JSONObject body = createNotificationBody(request);
		HttpEntity<String> jsonRequest = new HttpEntity<>(body.toString());

		CompletableFuture<FirebaseResponse> pushNotification = pushNotificationsService.send(jsonRequest);
		CompletableFuture.allOf(pushNotification).join();

		try {
			FirebaseResponse firebaseResponse = pushNotification.get();
			if (firebaseResponse.getSuccess() == 1) {
				log.info("push notification sent ok!");
				return new SendTestNotificationResponse();
			} else {
				log.error("error sending push notifications: " + firebaseResponse.toString());
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
		} catch (InterruptedException e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		} catch (ExecutionException e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	private JSONObject createNotificationBody(SendTestNotificationRequest request) {
		JSONObject body = new JSONObject();
		// JsonArray registration_ids = new JsonArray();
		// body.put("registration_ids", registration_ids);
		body.put("to", request.getDeviceToken());
		body.put("priority", "high");
		// body.put("dry_run", true);

		JSONObject notification = new JSONObject();
		notification.put("body", "Hello from Bananaa!");
		notification.put("title", "Hi there");
		notification.put("sound", "default");

		JSONObject data = new JSONObject();
		for (Data dataOb : request.getData()) {
			data.put(dataOb.getKey(), dataOb.getValue());
		}

		body.put("notification", notification);
		body.put("data", data);
		return body;
	}

}
