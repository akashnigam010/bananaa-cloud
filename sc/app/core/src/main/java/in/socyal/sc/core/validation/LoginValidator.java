package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.personnel.request.UserSignOnRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.type.LoginErrorCodeType;

@Component
public class LoginValidator {
	public void validateThirdPartyLoginRequest(String code) throws BusinessException {
		if (StringUtils.isEmpty(code)) {
			throw new BusinessException(LoginErrorCodeType.CODE_NOT_FOUND);
		}
	}

	public void validateSignOnLoginRequest(UserSignOnRequest request) throws BusinessException {
		if (StringUtils.isEmpty(request.getUserName())) {
			throw new BusinessException(LoginErrorCodeType.USERNAME_NOT_FOUND);
		}

		if (StringUtils.isEmpty(request.getPassword())) {
			throw new BusinessException(LoginErrorCodeType.PASSWORD_NOT_FOUND);
		}
	}
}
