package in.socyal.sc.helper.security.jwt;

import in.socyal.sc.api.helper.exception.BusinessException;

public interface JwtTokenHelper {
	public boolean isUserLoggedIn();
	public boolean isGuestLoggedIn();
	public Integer getUserId() throws BusinessException;
	public String getFirstName() throws BusinessException;
	public String getNameId() throws BusinessException;
	public void setAuthUser(String jwtToken);
}
