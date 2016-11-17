package in.socyal.sc.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.socyal.sc.api.bo.personnel.dto.LoggedInUser;
import in.socyal.sc.api.bo.personnel.request.UserSignOnRequest;
import in.socyal.sc.api.bo.personnel.response.UserSignOnResponse;
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
	public UserSignOnResponse login(UserSignOnRequest request, Boolean isPinSignOn) throws BusinessException {
		UserSignOnResponse response = new UserSignOnResponse();
		LoggedInUser user = null;
		if (isPinSignOn) {
			user = mapper.map(dao.login(request, isPinSignOn));
		} else {
			user = mapper.map(dao.login(request, isPinSignOn));
			user.setAuthorizedToManage(true);
		}
		response.setUser(user);
		/*String token = JwtHelper.createJsonWebToken(user.getName(), RoleType.getRoleById(user.getRoleId()).getRole(),
				1L);
		response.setToken(token);*/
		return response;
	}
}
