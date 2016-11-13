package in.socyal.sc.core.validation;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.bo.personnel.request.UserSignOnRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.type.LoginErrorCodeType;

@Component
public class LoginValidator {
	public void validateLoginService(UserSignOnRequest request, boolean isPinSignOn) throws BusinessException {
		if (!isPinSignOn) {
			if (request.getAccessCode() == null) {
				throw new BusinessException(LoginErrorCodeType.ACCESS_CODE_EMPTY);
			}
		}
		if (request.getPin() == null) {
			throw new BusinessException(LoginErrorCodeType.PIN_EMPTY);
		}
	}
}
