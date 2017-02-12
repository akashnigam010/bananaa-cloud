package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.api.type.error.LoginErrorCodeType;

@Component
public class NotificationValidator {
	public void validateSendNotificationRequest(SendTestNotificationRequest request) {
		if (StringUtils.isEmpty(request.getDeviceToken())) {
			throw new BusinessException(LoginErrorCodeType.FB_ACCESS_TOKEN_NOT_FOUND);
		}
	}
}
