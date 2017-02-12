package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.BusinessLoginRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.LoginErrorCodeType;

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

	public void validateBusinessLoginRequest(BusinessLoginRequest request) {
		if (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
