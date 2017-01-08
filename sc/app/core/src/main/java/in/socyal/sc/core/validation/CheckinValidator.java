package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.LikeCheckinRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class CheckinValidator {
	@Autowired JwtTokenHelper jwtHelper;

	public void validateValidateCheckinRequest(ValidateCheckinRequest request) {
		RoleType role = RoleType.getRole(jwtHelper.getUserName());
		if (role == RoleType.GUEST) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
		if (request.getLocation() == null || request.getLocation().getLatitude() == null
				|| request.getLocation().getLongitude() == null || StringUtils.isEmpty(request.getQrCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateConfirmCheckinRequest(ConfirmCheckinRequest request) {
		if (request.getLocation() == null || request.getLocation().getLatitude() == null
				|| request.getLocation().getLongitude() == null || StringUtils.isEmpty(request.getQrCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateCancelCheckinRequest(CancelCheckinRequest request) {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateCheckinRequest(CheckinRequest request) {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateLikeCheckinRequest(LikeCheckinRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
