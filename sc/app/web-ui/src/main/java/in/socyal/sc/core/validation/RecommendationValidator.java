package in.socyal.sc.core.validation;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

@Component
public class RecommendationValidator extends Validator {
	private static final Logger LOG = Logger.getLogger(RecommendationValidator.class);
	@Autowired
	JwtTokenHelper jwtTokenHelper;

	public void validateAddRecommendationRequest(EditRecommendationRequest request, String authToken)
			throws BusinessException {
		validateUserAndThrowException(authToken);
		if (request.getDishId() == null) {
			LOG.error("Dish Id not found while validating add recommendation request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateUpdateRecommendationRequest(EditRecommendationRequest request, String authToken)
			throws BusinessException {
		validateUserAndThrowException(authToken);
		if (request.getRcmdnId() == null) {
			LOG.error("Recommendation Id not found while validating update recommendation request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateRemoveRecommendationRequest(EditRecommendationRequest request, String authToken)
			throws BusinessException {
		validateUserAndThrowException(authToken);
		if (request.getRcmdnId() == null) {
			LOG.error("Recommendation Id not found while validating remove recommendation request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetRecommendationsRequest(GetRecommendationRequest request) throws BusinessException {
		if (request.getMerchantId() == null) {
			LOG.error("Merchant Id not found while validating get recommendations request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getPage() == null) {
			LOG.error("Page number not found while validating get recommendations request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetMyRecommendationsRequest(GetRecommendationRequest request, String authToken)
			throws BusinessException {
		validateUser(authToken);
		if (request.getMerchantId() == null) {
			LOG.error("Merchant Id not found while validating get my recommendations request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getPage() == null) {
			LOG.error("Page number not found while validating get my recommendations request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
