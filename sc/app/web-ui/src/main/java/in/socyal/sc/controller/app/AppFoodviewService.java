package in.socyal.sc.controller.app;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.FoodviewsResponse;
import in.socyal.sc.api.merchant.response.UserFoodviewsResponse;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.FoodviewRequest;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.app.rcmdn.RecommendationDelegate;
import in.socyal.sc.controller.MerchantService;
import in.socyal.sc.core.validation.RecommendationValidator;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.security.jwt.JwtHelper;

@RestController
@RequestMapping(value = "/bna/foodview")
public class AppFoodviewService {
	private static final Logger LOG = Logger.getLogger(AppFoodviewService.class);
	@Autowired
	RecommendationValidator validator;
	@Autowired
	RecommendationDelegate delegate;
	@Autowired
	ResponseHelper responseHelper;
	@Autowired
	JwtHelper jwtDetailsHelper;
	
	@RequestMapping(value = "/getMyFoodviews", method = RequestMethod.POST, headers = "Accept=application/json")
	public FoodviewsResponse getMyFoodviews(@RequestBody GetRecommendationRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/bna/foodview/getMyFoodviews");
		FoodviewsResponse response = new FoodviewsResponse();
		try {
			validator.validateGetMyFoodviewsRequestApp(request);
			response = delegate.getMyRecommendations(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getMyFoodview", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemRecommendationResponse getMyFoodview(@RequestBody GetRecommendationRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/bna/foodview/getMyFoodview");
		ItemRecommendationResponse response = new ItemRecommendationResponse();
		try {
			validator.validateGetMyFoodviewRequestApp(request);
			response = delegate.getMyDishRecommendations(request.getItemId());
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getOtherUsersFoodviews", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserFoodviewsResponse getOtherUsersFoodviews(@RequestBody GetRecommendationRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/bna/foodview/getOtherUsersFoodviews");
		UserFoodviewsResponse response = new UserFoodviewsResponse();
		try {
			validator.validateGetUserFoodviewRequestApp(request);
			response = delegate.getUsersFoodviewsForItem(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/saveRating", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveRating(@RequestBody RatingRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateRatingRequestApp(request);
			delegate.saveRating(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/saveFoodview", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveFoodview(@RequestBody FoodviewRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateSaveFoodviewRequestApp(request);
			delegate.saveFoodview(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/deleteFoodview", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveReview(@RequestBody IdRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateDeleteFoodviewRequestApp(request);
			delegate.deleteFoodview(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
}
