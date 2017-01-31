package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.business.request.BusinessApproveCheckinRequest;
import in.socyal.sc.api.checkin.business.request.BusinessCancelCheckinRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinDetailsRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinHistoryRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinsRequest;
import in.socyal.sc.api.checkin.request.AroundMeFeedsRequest;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.GetMerchantCheckinsRequest;
import in.socyal.sc.api.checkin.request.LikeCheckinRequest;
import in.socyal.sc.api.checkin.request.MyFeedsRequest;
import in.socyal.sc.api.checkin.request.ProfileFeedsRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class CheckinValidator {
	@Autowired
	JwtTokenHelper jwtHelper;

	public void validateGetMerchantCheckinsRequest(GetMerchantCheckinsRequest request) {
		// public call - no authentication required
		if (request.getId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateValidateCheckinRequest(ValidateCheckinRequest request) {
		validateIfLoggedInUser();
		if (request.getLocation() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
		if (request.getLocation().getLatitude() == null || request.getLocation().getLongitude() == null
				|| StringUtils.isEmpty(request.getQrCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateConfirmCheckinRequest(ConfirmCheckinRequest request) {
		validateIfLoggedInUser();
		if (request.getLocation() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
		if (request.getLocation().getLatitude() == null || request.getLocation().getLongitude() == null
				|| StringUtils.isEmpty(request.getQrCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateCancelCheckinRequest(CancelCheckinRequest request) {
		validateIfLoggedInUser();
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateCheckinRequest(CheckinRequest request) {
		validateIfLoggedInUser();
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateLikeCheckinRequest(LikeCheckinRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateAroundMeFeedsRequest(AroundMeFeedsRequest request) throws BusinessException {
		// public call - no authentication required
		if (request.getLocation() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
		if (request.getLocation().getLatitude() == null || request.getLocation().getLongitude() == null
				|| request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateMyFeedsRequest(MyFeedsRequest request) {
		validateIfLoggedInUser();
		if (request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateProfileFeedsRequest(ProfileFeedsRequest request) {
		// public call - no authentication required
		if (request.getUserId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetBusinessCheckinsRequest(GetBusinessCheckinsRequest request) {
		if (request.getDateIdentifier() == null || request.getPage() == null || request.getDateIdentifier() < 0
				|| request.getPage() <= 0) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	/**
	 * check if user is logged in or not. Throws exception if user is not logged
	 * in
	 */
	private void validateIfLoggedInUser() {
		RoleType role = RoleType.getRole(jwtHelper.getUserName());
		if (role == RoleType.GUEST) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
	}

	public void validateGetBusinessCheckinDetailsRequest(GetBusinessCheckinDetailsRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetBusinessCheckinHistoryRequest(GetBusinessCheckinHistoryRequest request) {
		if (request.getUserId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateBusinessCancelCheckinRequest(BusinessCancelCheckinRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateBusinessApproveCheckinRequest(BusinessApproveCheckinRequest request) {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
