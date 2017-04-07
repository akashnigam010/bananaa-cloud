package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.user.request.FollowRequest;
import in.socyal.sc.api.user.request.GetMyFriendsRequest;
import in.socyal.sc.api.user.request.GetPublicProfileRequest;
import in.socyal.sc.api.user.request.SaveRegistrationIdRequest;
import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.api.user.request.UnFollowRequest;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

@Component
public class UserValidator {
	@Autowired
	JwtTokenHelper jwtHelper;
	
	public void validateSearchFriendRequest(SearchFriendRequest request) throws BusinessException {
		if (StringUtils.isEmpty(request.getSearchString())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetMyFriendsRequest(GetMyFriendsRequest request) throws BusinessException {
		validateIfLoggedInUser();
		if (request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetPublicProfileRequest(GetPublicProfileRequest request) throws BusinessException {
		if (request.getUserId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateSearchUserRequest(SearchFriendRequest request) throws BusinessException {
		if (StringUtils.isEmpty(request.getSearchString())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	/**
	 * check if user is logged in or not.
	 * Throws exception if user is not logged in
	 */
	private void validateIfLoggedInUser() throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
	}

	public void validateRegistrationIdRequest(SaveRegistrationIdRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getRegistrationId())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateFollowRequest(FollowRequest request) throws BusinessException {
		if (request.getUserId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateUnFollowRequest(UnFollowRequest request) throws BusinessException {
		if (request.getUserId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
