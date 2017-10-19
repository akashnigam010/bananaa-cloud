package in.socyal.sc.controller.app;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.MyFoodviewsResponse;
import in.socyal.sc.api.merchant.response.UserFoodviewsResponse;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
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
	public MyFoodviewsResponse getMyFoodviews(@RequestBody GetRecommendationRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/bna/foodview/getMyFoodviews");
		MyFoodviewsResponse response = new MyFoodviewsResponse();
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
			response = delegate.getMyDishRecommendation(request.getItemId());
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
			response = delegate.getUsersFoodviews(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return responseHelper.failure(response, e);
		}
	}
}
