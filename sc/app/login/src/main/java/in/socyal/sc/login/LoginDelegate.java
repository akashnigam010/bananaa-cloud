package in.socyal.sc.login;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.response.LoginResponse;

public interface LoginDelegate {
	/**
	 * Firebase Login
	 * 
	 * @param request
	 * @return
	 */
	public LoginResponse firebaseLogin(IdTokenRequest request) throws BusinessException;

	/**
	 * Federated login
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	LoginResponse federatedLogin(LoginRequest request) throws BusinessException;
}
