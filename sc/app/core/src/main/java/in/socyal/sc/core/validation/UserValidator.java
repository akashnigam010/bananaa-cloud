package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.user.request.GetMyFriendsRequest;
import in.socyal.sc.api.user.request.GetPublicProfileRequest;
import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class UserValidator {
	@Autowired
	JwtTokenHelper jwtHelper;
	
	public void validateSearchFriendRequest(SearchFriendRequest request) {
		if (StringUtils.isEmpty(request.getSearchString())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetMyFriendsRequest(GetMyFriendsRequest request) {
		validateIfLoggedInUser();
		if (request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetPublicProfileRequest(GetPublicProfileRequest request) {
		if (request.getUserId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	/**
	 * check if user is logged in or not.
	 * Throws exception if user is not logged in
	 */
	private void validateIfLoggedInUser() {
		RoleType role = RoleType.getRole(jwtHelper.getUserName());
		if (role == RoleType.GUEST) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
	}
}
