package in.socyal.sc.login;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.response.LoginResponse;

public interface LoginDelegate {
	/**
	 * Set details when login is skipped
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public LoginResponse skipLogin() throws BusinessException;

	/**
	 * Fetch user details from FB using access token
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	// public LoginResponse fbLogin(LoginRequest request) throws
	// BusinessException;

	/**
	 * Business login
	 * 
	 * @param request
	 * @return
	 */
	// public BusinessLoginResponse businessLogin(BusinessLoginRequest request)
	// throws BusinessException;

	/**
	 * Firebase Login
	 * 
	 * @param request
	 * @return
	 */
	public LoginResponse firebaseLogin(IdTokenRequest request) throws BusinessException;
}
