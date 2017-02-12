package in.socyal.sc.notification;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;

public interface NotificationDelegate {

	/**
	 * Send notification with data
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public SendTestNotificationResponse sendNotificationWithData(SendTestNotificationRequest request)
			throws BusinessException;
	
	/**
	 * Send only data message
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public SendTestNotificationResponse sendDataMessage(SendTestNotificationRequest request)
			throws BusinessException;
}
