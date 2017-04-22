package in.socyal.sc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.UserDao;

@Service
public class UserDelegateImpl implements UserDelegate {

	@Autowired
	UserDao userDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public UserDetailsResponse getUserDetails(String userNameId) throws BusinessException {
		UserDto userDto = userDao.getUserByNameId(userNameId);
		UserDetailsResponse response = new UserDetailsResponse();
		response.setUser(userDto);
		response.setRecommendations(userDto.getRecommendations());
		response.setTotalRecommendations(userDto.getRecommendations().size());
		return response;
	}
}
