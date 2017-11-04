package in.socyal.sc.login;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.login.request.IdTokenRequest;
import in.socyal.sc.api.login.request.LoginRequest;
import in.socyal.sc.api.login.request.MLoginRequest;
import in.socyal.sc.api.login.response.LoginResponse;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.api.user.request.ProfileRequest;

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
	
	/**
	 * Manual login using email, password
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	LoginResponse manualLogin(MLoginRequest request) throws BusinessException;
	
	/**
	 * Register for Bananaa
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	LoginResponse register(MLoginRequest request) throws BusinessException;
	
	/**
	 * Forget Password
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	StatusResponse resetPassword(MLoginRequest request) throws BusinessException;

	public void saveProfile(ProfileRequest request) throws BusinessException;
}
