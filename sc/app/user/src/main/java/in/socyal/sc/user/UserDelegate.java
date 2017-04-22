package in.socyal.sc.user;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;

public interface UserDelegate {
	UserDetailsResponse getUserDetails(String userNameId) throws BusinessException;
}
