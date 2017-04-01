package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.business.request.SaveBusinessRegistrationIdRequest;
import in.socyal.sc.api.merchant.business.response.SaveBusinessRegistrationIdResponse;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.merchant.response.Dish;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.PopularItemsResponse;
import in.socyal.sc.api.merchant.response.Recommendation;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.merchant.response.SaveMerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.core.validation.MerchantValidator;
import in.socyal.sc.helper.JsonHelper;

@RestController
@RequestMapping(value = "/socyal/merchant")
public class MerchantService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	MerchantDelegate delegate;
	@Autowired
	ResponseHelper responseHelper;
	@Autowired
	MerchantValidator validator;

	@RequestMapping(value = "/getMerchants", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetMerchantListResponse getMerchants(@RequestBody GetMerchantListRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/getMerchants");
		GetMerchantListResponse response = new GetMerchantListResponse();
		try {
			validator.validateGetMerchantRequest(request);
			response = delegate.getMerchants(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}

	}

	@RequestMapping(value = "/getMerchantDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantDetailsResponse getMerchantDetails(@RequestBody MerchantDetailsRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/getMerchantDetails");
		MerchantDetailsResponse response = new MerchantDetailsResponse();
		try {
			validator.validateGetMerchantDetailsRequest(request);
			response = delegate.getMerchantDetails(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	@RequestMapping(value = "/searchMerchant", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchMerchantResponse searchMerchant(@RequestBody SearchMerchantRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/searchMerchant");
		SearchMerchantResponse response = new SearchMerchantResponse();
		try {
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				response = delegate.searchMerchant(request);
			}			
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getTrendingRestaurants", method = RequestMethod.GET, headers = "Accept=application/json")
	public SearchMerchantResponse getTrendingRestaurants() {
		SearchMerchantResponse response = new SearchMerchantResponse();
		try {
			SearchMerchantRequest request = new SearchMerchantRequest();
			request.setSearchString("as");
			response = delegate.searchMerchant(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getMyRecommendations", method = RequestMethod.POST, headers = "Accept=application/json")
	public RecommendationResponse getMyRecommendations(@RequestBody MerchantDetailsRequest request) {
		RecommendationResponse response = new RecommendationResponse();
		try {
			response = getMyRecommendation();
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getPopularItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public PopularItemsResponse getPopularItems(@RequestBody MerchantDetailsRequest request) {
		PopularItemsResponse response = new PopularItemsResponse();
		try {
			response = getPopularDishes();
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	private PopularItemsResponse getPopularDishes() throws BusinessException {
		PopularItemsResponse response = new PopularItemsResponse();
		Dish dish = new Dish();
		dish.setId(1);
		dish.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish.setName("Joojeh Kebab");
		dish.setNameId("joojeh-kebeb");
		dish.setRecommendations(22);
		response.getItems().add(dish);
		Dish dish2 = new Dish();
		dish2.setId(2);
		dish2.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish2.setName("Lowley Sirley");
		dish2.setNameId("lowley-shirley");
		dish2.setRecommendations(15);
		response.getItems().add(dish2);
		Dish dish3 = new Dish();
		dish3.setId(3);
		dish3.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish3.setName("Arrabiata Pasta");
		dish3.setNameId("arrabiata-pasta");
		dish3.setRecommendations(12);
		response.getItems().add(dish3);
		Dish dish4 = new Dish();
		dish4.setId(4);
		dish4.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish4.setName("Mango Delight Punch");
		dish4.setNameId("mango-delight");
		dish4.setRecommendations(10);
		response.getItems().add(dish4);
		
		Dish dish5 = new Dish();
		dish5.setId(2);
		dish5.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish5.setName("Lowley Sirley");
		dish5.setNameId("lowley-shirley");
		dish5.setRecommendations(15);
		response.getItems().add(dish5);
		Dish dish6 = new Dish();
		dish6.setId(3);
		dish6.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish6.setName("Arrabiata Pasta");
		dish6.setNameId("arrabiata-pasta");
		dish6.setRecommendations(12);
		response.getItems().add(dish6);
		Dish dish7 = new Dish();
		dish7.setId(4);
		dish7.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish7.setName("Mango Delight Punch");
		dish7.setNameId("mango-delight");
		dish7.setRecommendations(10);
		response.getItems().add(dish7);
		
		Dish dish8 = new Dish();
		dish8.setId(2);
		dish8.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish8.setName("Lowley Sirley");
		dish8.setNameId("lowley-shirley");
		dish8.setRecommendations(15);
		response.getItems().add(dish8);
		Dish dish9 = new Dish();
		dish9.setId(3);
		dish9.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish9.setName("Arrabiata Pasta");
		dish9.setNameId("arrabiata-pasta");
		dish9.setRecommendations(12);
		response.getItems().add(dish9);
		Dish dish10 = new Dish();
		dish10.setId(4);
		dish10.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish10.setName("Mango Delight Punch");
		dish10.setNameId("mango-delight");
		dish10.setRecommendations(10);
		response.getItems().add(dish10);
		return response;
	}
	
	private RecommendationResponse getMyRecommendation() throws BusinessException {
		RecommendationResponse response = new RecommendationResponse();
		Recommendation rcmdn1 = new Recommendation();
		rcmdn1.setId(11);
		rcmdn1.setItemId(21);
		rcmdn1.setName("Mutton Seekh Kebab");
		rcmdn1.setDescription("Very tastey and juicy, as if drops from heaven");
		response.getRecommendations().add(rcmdn1);
		Recommendation rcmdn2 = new Recommendation();
		rcmdn2.setId(12);
		rcmdn1.setItemId(22);
		rcmdn2.setName("Galawati Kebab");
		response.getRecommendations().add(rcmdn2);
		Recommendation rcmdn3 = new Recommendation();
		rcmdn3.setId(13);
		rcmdn1.setItemId(23);
		rcmdn3.setName("Grilled Mushroom");
		rcmdn3.setDescription("Very tastey and juicy, as if drops from heaven");
		response.getRecommendations().add(rcmdn3);
		return response;		
	}

	// Testing purpose
	@RequestMapping(value = "/saveMerchantDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public SaveMerchantDetailsResponse saveMerchantDetails(@RequestBody SaveMerchantDetailsRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/saveMerchantDetails");
		SaveMerchantDetailsResponse response = new SaveMerchantDetailsResponse();
		try {
			delegate.saveMerchantDetails(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	/**
	 * This method is used to save REGISTRATION_ID for a respective merchant logged in device
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveBusinessRegistrationId", method = RequestMethod.POST, headers = "Accept=application/json")
	public SaveBusinessRegistrationIdResponse saveBusinessRegistrationId(@RequestBody SaveBusinessRegistrationIdRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/saveBusinessRegistrationId");
		SaveBusinessRegistrationIdResponse response = new SaveBusinessRegistrationIdResponse();
		try {
			validator.validateSaveBusinessRegistrationIdRequest(request);
			response = delegate.saveBusinessRegistrationId(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
}
