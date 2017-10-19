package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.ReviewRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

@Component
public class RecommendationValidator extends Validator {
	private static final Logger LOG = Logger.getLogger(RecommendationValidator.class);
	@Autowired
	JwtTokenHelper jwtTokenHelper;

	public void validateRatingRequest(RatingRequest request, String authToken)
			throws BusinessException {
		validateUserAndThrowException(authToken);
		if (request.getId() == null || request.getRating() == null) {
			LOG.error("Dish Id or rating value not found while validating save rating request request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateReviewRequest(ReviewRequest request, String authToken)
			throws BusinessException {
		validateUserAndThrowException(authToken);
		if (request.getId() == null || StringUtils.isBlank(request.getDescription())) {
			LOG.error("Dish Id or review desc not found while validating save rating request request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
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
	
	public void validateGetMyFoodviewsRequestApp(GetRecommendationRequest request)
			throws BusinessException {
		if (!jwtTokenHelper.isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
		if (request.getMerchantId() == null) {
			LOG.error("Merchant Id not found while validating get my foodviews request - app");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getPage() == null) {
			LOG.error("Page number not found while validating get my foodviews request - app");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateGetMyFoodviewRequestApp(GetRecommendationRequest request)
			throws BusinessException {
		if (!jwtTokenHelper.isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
		if (request.getItemId() == null) {
			LOG.error("Item Id not found while validating get my foodview request - app");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
	
	public void validateGetUserFoodviewRequestApp(GetRecommendationRequest request)
			throws BusinessException {
		if (!jwtTokenHelper.isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.LOGIN_REQUIRED);
		}
		if (request.getItemId() == null) {
			LOG.error("Item Id not found while validating get users foodview request - app");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getPage() == null) {
			LOG.error("Page number not found while validating get my recommendations request - app");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateGetMyItemRecommendationRequest(GetRecommendationRequest request, String authToken)
			throws BusinessException {
		validateUser(authToken);
		if (request.getItemId() == null) {
			LOG.error("Item Id not found while validating get my item recommendation request");
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
