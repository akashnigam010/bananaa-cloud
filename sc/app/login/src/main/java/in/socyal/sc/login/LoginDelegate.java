package in.socyal.sc.login;

import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.BusinessLoginResponse;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface LoginDelegate {
	/**
	 * Set details when login is skipped
	 * @return
	 * @throws BusinessException
	 */
	public LoginResponse skipLogin() throws BusinessException;
	
	/**
	 * Fetch user details from FB using access token
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public LoginResponse fbLogin(LoginRequest request) throws BusinessException;

	/**
	 * Business login
	 * @return
	 */
	public BusinessLoginResponse businessLogin();
}
