package in.socyal.sc.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRecommendationsRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.request.MessageRequest;
import in.socyal.sc.api.manage.request.UpdateItemRequest;
import in.socyal.sc.api.type.error.GenericErrorCodeType;

@Component
public class ManageValidator {
	public void validateAddRequest(AddRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getName())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateAddItemRequest(AddItemRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getName()) || request.getMerchantId() == null || request.getIsActive() == null
				|| StringUtils.isBlank(request.getImageUrl()) || StringUtils.isBlank(request.getThumbnail())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateSendMessageRequest(MessageRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getEmail())
				|| StringUtils.isBlank(request.getMessage())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateAddRecommendationsRequest(AddRecommendationsRequest request) throws BusinessException {
		if (request.getItemId() == null || request.getRating() == null || request.getRcmdCount() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateUpdateItemRequest(UpdateItemRequest request) throws BusinessException {
		if (request.getId() == null || StringUtils.isBlank(request.getName())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validateIdRequest(IdRequest request) throws BusinessException {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
