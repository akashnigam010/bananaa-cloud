package in.socyal.sc.notification;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.jboss.logging.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.Data;
import in.socyal.sc.api.login.request.NotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.helper.notification.FirebaseResponse;
import in.socyal.sc.helper.notification.NotificationsService;

@Service
public class NotificationDelegateImpl implements NotificationDelegate {
	private static final Logger log = Logger.getLogger(NotificationDelegateImpl.class);
	private final static Integer TIME_TO_LIVE = 86400;
	private final static String PRIORITY = "high";

	@Autowired
	NotificationsService pushNotificationsService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SendTestNotificationResponse sendDataNotification(NotificationRequest request) throws BusinessException {
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

	private JSONObject createDefaultMessageBody(NotificationRequest request) {
		JSONObject body = new JSONObject();
		JsonArray registrationIds = new JsonArray();
		for (String registrationId : request.getDeviceTokens()) {
			registrationIds.add(registrationId);
		}

		// registrationIds.
		// JsonArray registration_ids = new JsonArray();
		body.put("registration_ids", registrationIds);
		// body.put("to", request.getDeviceToken());
		body.put("priority", PRIORITY);
		body.put("time_to_live", TIME_TO_LIVE);
		// body.put("dry_run", true);

		// body.put("notification", createNotificationPayload(request));
		// body.put("data", createDataPayload(request));
		return body;
	}

	private JSONObject createDataPayload(NotificationRequest request) {
		JSONObject data = new JSONObject();
		data.put("body", request.getBody());
		data.put("title", request.getTitle());
		for (Data dataOb : request.getData()) {
			data.put(dataOb.getKey(), dataOb.getValue());
		}
		return data;
	}
}
