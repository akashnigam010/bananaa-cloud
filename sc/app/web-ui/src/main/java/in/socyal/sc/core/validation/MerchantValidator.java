package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.business.request.SaveBusinessRegistrationIdRequest;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;

@Component
public class MerchantValidator {
	public void validateGetMerchantRequest(GetMerchantListRequest request) {
		// FIXME : add validation logic
	}

	public void validateGetMerchantDetailsRequest(DetailsRequest request) throws BusinessException {
		if (StringUtils.isEmpty(request.getMerchantNameId())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateSaveBusinessRegistrationIdRequest(SaveBusinessRegistrationIdRequest request) throws BusinessException {
		if (request.getRegistrationId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
