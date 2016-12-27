package in.socyal.sc.user;

import in.socyal.sc.api.user.response.UserProfileResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface UserDelegate {
	/**
	 * Fetch logged in user profile details
	 * @return
	 * @throws BusinessException
	 */
	public UserProfileResponse getProfile() throws BusinessException;
}
