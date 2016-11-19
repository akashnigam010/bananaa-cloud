package in.socyal.sc.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.personnel.dto.UserAccessTokenDto;
import in.socyal.sc.api.personnel.request.UserSignOnRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.login.dao.LoginDao;
import in.socyal.sc.login.mapper.LoginMapper;

@Service
public class LoginDelegateImpl implements LoginDelegate {
	@Autowired
	LoginDao dao;
	@Autowired
	LoginMapper mapper;

	@Override
	public UserAccessTokenDto login(UserSignOnRequest request) throws BusinessException {
		UserAccessTokenDto accessToken = new UserAccessTokenDto();
		return accessToken;
	}
}
