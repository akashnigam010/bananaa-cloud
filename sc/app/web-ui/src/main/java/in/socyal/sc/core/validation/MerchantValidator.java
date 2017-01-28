package in.socyal.sc.core.validation;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.type.GenericErrorCodeType;

@Component
public class MerchantValidator {
	public void validateGetMerchantRequest(GetMerchantListRequest request) {
		// FIXME : add validation logic
	}

	public void validateGetMerchantDetailsRequest(MerchantDetailsRequest request) {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
