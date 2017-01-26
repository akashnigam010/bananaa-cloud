package in.socyal.sc.login;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
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
	public SendTestNotificationResponse sendNotificationWithData(SendTestNotificationRequest request)
			throws BusinessException {
		JSONObject body = createDefaultMessageBody(request);
		body.put("notification", createNotificationPayload(request));
		body.put("data", createDataPayload(request));
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

	@Override
	public SendTestNotificationResponse sendDataMessage(SendTestNotificationRequest request) throws BusinessException {
		JSONObject body = createDefaultMessageBody(request);
		body.put("data", createDataPayload(request));
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

	private JSONObject createDefaultMessageBody(SendTestNotificationRequest request) {
		JSONObject body = new JSONObject();
		// JsonArray registration_ids = new JsonArray();
		// body.put("registration_ids", registration_ids);
		body.put("to", request.getDeviceToken());
		body.put("priority", "high");
		body.put("time_to_live", 86400);
		// body.put("dry_run", true);

		// body.put("notification", createNotificationPayload(request));
		// body.put("data", createDataPayload(request));
		return body;
	}

	private JSONObject createDataPayload(SendTestNotificationRequest request) {
		JSONObject data = new JSONObject();
		data.put("body", "This is a data message");
		data.put("title", "Hi from Bananaa!");
		for (Data dataOb : request.getData()) {
			data.put(dataOb.getKey(), dataOb.getValue());
		}
		return data;
	}

	private JSONObject createNotificationPayload(SendTestNotificationRequest request) {
		JSONObject notification = new JSONObject();
		notification.put("body", "This is a notification message with data");
		notification.put("title", "Hi from Bananaa!");
		notification.put("sound", "default");
		if (StringUtils.isNotEmpty(request.getClickAction())) {
			notification.put("click_action", request.getClickAction());
		}
		return notification;
	}
}
