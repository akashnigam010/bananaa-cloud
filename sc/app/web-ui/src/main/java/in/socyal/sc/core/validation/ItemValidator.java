package in.socyal.sc.core.validation;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;

@Component
public class ItemValidator extends Validator {
	private static final Logger LOG = Logger.getLogger(ItemValidator.class);

	public void validateSearchItemsRequest(SearchRequest request) throws BusinessException {
		if (request.getMerchantId() == null) {
			LOG.error("Merchant Id not found while validating search items at a restaurant request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateGetPopularItemsRequest(TrendingRequest request) throws BusinessException {
		if (request.getMerchantId() == null) {
			LOG.error("Merchant Id not found while validating get popular items request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getPage() == null) {
			LOG.error("Page number not found while validating get popular items request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
		
		if (request.getResultsPerPage() == null) {
			LOG.error("Results Per Page number not found while validating get popular items request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
