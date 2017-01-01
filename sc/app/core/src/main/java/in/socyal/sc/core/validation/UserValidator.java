package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.user.request.SearchFriendRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class UserValidator {

	public void validateSearchUserRequest(SearchFriendRequest request) {
		if (StringUtils.isEmpty(request.getSearchString())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
