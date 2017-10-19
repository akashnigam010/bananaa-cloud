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
import in.socyal.sc.api.merchant.response.MyFoodviewsResponse;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.ReviewRequest;
import in.socyal.sc.api.response.StatusResponse;
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

	@RequestMapping(value = "/saveRating", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveRating(@RequestBody RatingRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateRatingRequest(request, blc);
			delegate.saveRating(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/saveReview", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveReview(@RequestBody ReviewRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateReviewRequest(request, blc);
			delegate.saveReview(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse addRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		StatusResponse response = new StatusResponse();
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
	public StatusResponse updateRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		StatusResponse response = new StatusResponse();
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
	public StatusResponse removeRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		StatusResponse response = new StatusResponse();
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
	public MyFoodviewsResponse getMyRecommendations(@RequestBody GetRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		MyFoodviewsResponse response = new MyFoodviewsResponse();
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
			response = delegate.getMyDishRecommendation(request.getItemId());
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}
}
