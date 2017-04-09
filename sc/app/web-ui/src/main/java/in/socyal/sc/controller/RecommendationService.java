package in.socyal.sc.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.response.EditRecommendationResponse;
import in.socyal.sc.app.rcmdn.RecommendationDelegate;
import in.socyal.sc.core.validation.RecommendationValidator;

@RestController
@RequestMapping(value = "/socyal/recommendation")
public class RecommendationService {
	private static final Logger LOG = Logger.getLogger(RecommendationService.class);

	@Autowired
	ResponseHelper helper;
	@Autowired
	RecommendationValidator validator;
	@Autowired
	RecommendationDelegate delegate;

	@RequestMapping(value = "/addRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public EditRecommendationResponse addRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		EditRecommendationResponse response = new EditRecommendationResponse();
		try {
			validator.validateAddRecommendationRequest(request, blc);
			delegate.addRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/updateRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public EditRecommendationResponse updateRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		EditRecommendationResponse response = new EditRecommendationResponse();
		try {
			validator.validateUpdateRecommendationRequest(request, blc);
			delegate.updateRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/removeRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public EditRecommendationResponse removeRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		EditRecommendationResponse response = new EditRecommendationResponse();
		try {
			validator.validateRemoveRecommendationRequest(request, blc);
			delegate.removeRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getMyRecommendations", method = RequestMethod.POST, headers = "Accept=application/json")
	public RecommendationResponse getMyRecommendations(@RequestBody GetRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		RecommendationResponse response = new RecommendationResponse();
		try {
			validator.validateGetMyRecommendationsRequest(request, blc);
			response = delegate.getMyRecommendations(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getMyItemRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemRecommendationResponse getMyItemRecommendation(@RequestBody GetRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		ItemRecommendationResponse response = new ItemRecommendationResponse();
		try {
			validator.validateGetMyItemRecommendationRequest(request, blc);
			response = delegate.getMyDishRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}
}
