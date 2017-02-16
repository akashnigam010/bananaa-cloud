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
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.RoleType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

@Component
public class CheckinValidator {
	@Autowired
	JwtTokenHelper jwtHelper;

	public void validateGetMerchantCheckinsRequest(GetMerchantCheckinsRequest request) throws BusinessException {
		// public call - no authentication required
		if (request.getId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateValidateCheckinRequest(ValidateCheckinRequest request) throws BusinessException {
		validateIfLoggedInUser();
		if (request.getLocation() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
		if (request.getLocation().getLatitude() == null || request.getLocation().getLongitude() == null
				|| StringUtils.isEmpty(request.getQrCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateConfirmCheckinRequest(ConfirmCheckinRequest request) throws BusinessException {
		validateIfLoggedInUser();
		if (request.getLocation() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
		if (request.getLocation().getLatitude() == null || request.getLocation().getLongitude() == null
				|| StringUtils.isEmpty(request.getQrCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateCancelCheckinRequest(CancelCheckinRequest request) throws BusinessException {
		validateIfLoggedInUser();
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateCheckinRequest(CheckinRequest request) throws BusinessException {
		validateIfLoggedInUser();
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateLikeCheckinRequest(LikeCheckinRequest request) throws BusinessException {
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

	public void validateMyFeedsRequest(MyFeedsRequest request) throws BusinessException {
		validateIfLoggedInUser();
		if (request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateProfileFeedsRequest(ProfileFeedsRequest request) throws BusinessException {
		// public call - no authentication required
		if (request.getUserId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetBusinessCheckinsRequest(GetBusinessCheckinsRequest request) throws BusinessException {
		if (request.getDateIdentifier() == null || request.getPage() == null || request.getDateIdentifier() < 0
				|| request.getPage() <= 0) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	/**
	 * check if user is logged in or not. Throws exception if user is not logged
	 * in
	 */
	private void validateIfLoggedInUser() throws BusinessException {
		for (String role : jwtHelper.getRoles()) {
			RoleType roleType = RoleType.getRole(role);
			if (RoleType.GUEST == roleType) {
				throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
			} 
		}
	}

	public void validateGetBusinessCheckinDetailsRequest(GetBusinessCheckinDetailsRequest request) throws BusinessException {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetBusinessCheckinHistoryRequest(GetBusinessCheckinHistoryRequest request) throws BusinessException {
		if (request.getUserId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateBusinessCancelCheckinRequest(BusinessCancelCheckinRequest request) throws BusinessException {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateBusinessApproveCheckinRequest(BusinessApproveCheckinRequest request) throws BusinessException {
		if (request.getCheckinId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
