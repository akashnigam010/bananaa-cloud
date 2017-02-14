package in.socyal.sc.notification;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.NotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;

public interface NotificationDelegate {

	/**
	 * Send data message notification
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public SendTestNotificationResponse sendDataNotification(NotificationRequest request)
			throws BusinessException;
}
