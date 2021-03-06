package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.IdPageRequest;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.items.request.GetFoodSuggestionsRequest;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.user.request.ProfileRequest;

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

	public void validateIdRequest(IdRequest request) throws BusinessException {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateIdPageRequest(IdPageRequest request) throws BusinessException {
		if (request.getId() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateFoodSuggestionsRequest(GetFoodSuggestionsRequest request) throws BusinessException {
		if (request.getLocationId() == null || request.getIsCity() == null || request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateSearchMerchantsRequest(SearchMerchantRequest request) throws BusinessException {
		if (request.getLocationId() == null || request.getIsTagSearch() == null || request.getIsCity() == null
				|| request.getPage() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

	}
	
	public void validateSaveProfileRequest(ProfileRequest request) throws BusinessException {
		if (StringUtils.isEmpty(request.getName())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
