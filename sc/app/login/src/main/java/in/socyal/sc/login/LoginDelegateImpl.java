package in.socyal.sc.login;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.personnel.dto.FacebookUser;
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
	@Autowired
	OAuth2FbHelper fbHelper;

	@Override
	public UserAccessTokenDto login(UserSignOnRequest request) throws BusinessException {
		UserAccessTokenDto accessToken = new UserAccessTokenDto();
		return accessToken;
	}

	@Override
	public UserAccessTokenDto thirdPartyLogin(String code) throws BusinessException {
		try {
			FacebookUser fbUser = fbHelper.login(code);
			System.out.println(fbUser);
		} catch (IOException e) {
			throw new BusinessException();
		}
		UserAccessTokenDto accessTkn = new UserAccessTokenDto();
		return accessTkn;

	}

}
