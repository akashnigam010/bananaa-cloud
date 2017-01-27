package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.SendTestNotificationRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.type.LoginErrorCodeType;

@Component
public class LoginValidator {
	public void validateFbLoginRequest(LoginRequest request) {
		if (StringUtils.isEmpty(request.getFbId())) {
			throw new BusinessException(LoginErrorCodeType.USER_ID_NOT_FOUND);
		}

		if (StringUtils.isEmpty(request.getFbAccessToken())) {
			throw new BusinessException(LoginErrorCodeType.FB_ACCESS_TOKEN_NOT_FOUND);
		}
	}

	public void validateSendNotificationRequest(SendTestNotificationRequest request) {
		if (StringUtils.isEmpty(request.getDeviceToken())) {
			throw new BusinessException(LoginErrorCodeType.FB_ACCESS_TOKEN_NOT_FOUND);
		}
	}
}
