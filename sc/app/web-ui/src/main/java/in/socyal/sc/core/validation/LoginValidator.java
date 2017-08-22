package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.MLoginRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;

@Component
public class LoginValidator {
	public void validateLoginRequest(LoginRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getAccessToken()) || request.getClient() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateLoginRequest(MLoginRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateRegisterRequest(MLoginRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getEmail())
				|| StringUtils.isBlank(request.getPassword())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateResetPasswordRequest(MLoginRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getEmail())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
