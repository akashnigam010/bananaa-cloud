package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class CheckinValidator {

	public void validateValidateCheckinRequest(ValidateCheckinRequest request) {
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
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
