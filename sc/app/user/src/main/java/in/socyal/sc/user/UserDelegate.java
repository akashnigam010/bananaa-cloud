package in.socyal.sc.user;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.user.request.SavePreferencesRequest;

public interface UserDelegate {
	UserDetailsResponse getUserDetails(String userNameId) throws BusinessException;
	void saveUserPreferences(SavePreferencesRequest request) throws BusinessException;
	Integer getVegnonvegPreference() throws BusinessException;
}
