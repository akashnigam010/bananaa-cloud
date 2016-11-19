package in.socyal.sc.login;

import in.socyal.sc.api.personnel.dto.UserAccessTokenDto;
import in.socyal.sc.api.personnel.request.UserSignOnRequest;
import in.socyal.sc.helper.exception.BusinessException;

public interface LoginDelegate {
	public UserAccessTokenDto login(UserSignOnRequest request) throws BusinessException;
}
