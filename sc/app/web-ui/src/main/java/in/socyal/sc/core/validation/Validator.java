package in.socyal.sc.core.validation;

import org.springframework.beans.factory.annotation.Autowired;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

public abstract class Validator {
	@Autowired
	JwtTokenHelper jwtTokenHelper;

	public void validateUser(String authToken) {
		jwtTokenHelper.setAuthUser(authToken);
	}

	public void validateUserAndThrowException(String authToken) throws BusinessException {
		jwtTokenHelper.setAuthUser(authToken);
		if (!jwtTokenHelper.isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
	}

}
