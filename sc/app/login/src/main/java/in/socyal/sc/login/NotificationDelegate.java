package in.socyal.sc.login;

import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.api.login.response.SendTestNotificationResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface NotificationDelegate {

	/**
	 * Send test notification with data
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public SendTestNotificationResponse sendTestNotification(SendTestNotificationRequest request)
			throws BusinessException;
}
