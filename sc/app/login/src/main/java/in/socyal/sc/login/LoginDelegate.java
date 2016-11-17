package in.socyal.sc.login;

import in.socyal.sc.api.bo.personnel.request.UserSignOnRequest;
import in.socyal.sc.api.bo.personnel.response.UserSignOnResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface LoginDelegate {
	public UserSignOnResponse login(UserSignOnRequest request, Boolean isPinSignOn) throws BusinessException;
}
