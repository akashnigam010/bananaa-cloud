package in.socyal.sc.core.validation;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.personnel.request.UserSignOnRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.type.LoginErrorCodeType;

@Component
public class LoginValidator {
	public void validateLoginService(UserSignOnRequest request) throws BusinessException {
		if (request.getUserName() == null) {
			throw new BusinessException(LoginErrorCodeType.USERNAME_EMPTY);
		}
		
		if (request.getPassword() == null) {
			throw new BusinessException(LoginErrorCodeType.PASSWORD_EMPTY);
		}
	}
}
