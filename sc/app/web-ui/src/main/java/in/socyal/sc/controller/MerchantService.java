package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.request.GetMerchantListRequest;
import in.socyal.sc.api.merchant.request.SaveMerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.GetMerchantListResponse;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantResponse;
import in.socyal.sc.api.merchant.response.SaveMerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.merchant.response.StoriesResponse;
import in.socyal.sc.api.merchant.response.Story;
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
	public MerchantDetailsResponse getMerchantDetails(@RequestBody DetailsRequest request) {
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
	public SearchMerchantResponse searchMerchant(@RequestBody SearchRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/searchMerchant");
		SearchMerchantResponse response = new SearchMerchantResponse();
		try {
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				response = delegate.searchMerchant(request);
				if (response.getMerchants().size() == 0) {
					MerchantResponse noMatchFound = new MerchantResponse();
					noMatchFound.setId(-999);
					noMatchFound.setName("No match found");
					noMatchFound.setShortAddress("");
					response.getMerchants().add(noMatchFound);
				}
			}
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getTrendingRestaurants", method = RequestMethod.GET, headers = "Accept=application/json")
	public GetTrendingMerchantsResponse getTrendingRestaurants() {
		GetTrendingMerchantsResponse response = new GetTrendingMerchantsResponse();
		try {
			response = delegate.getTrendingMerchants();
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getStories", method = RequestMethod.GET, headers = "Accept=application/json")
	public StoriesResponse getStories() {
		StoriesResponse response = createStories();
		return responseHelper.success(response);
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
	
	private StoriesResponse createStories() {
		StoriesResponse response = new StoriesResponse();
		Story story1 = new Story();
		story1.setName("Bananaa - What the Fuzz!");
		story1.setImageUrl("https://bna-s3.s3.amazonaws.com/img/what-mini.jpg");
		story1.setUrl("/about");
		response.getStories().add(story1);
		Story story2 = new Story();
		story2.setName("Recommendations. But wait..how ?");
		story2.setImageUrl("https://bna-s3.s3.amazonaws.com/img/next.jpg");
		story2.setUrl("/how");
		response.getStories().add(story2);
//		Story story3 = new Story();
//		story3.setName("Where are we headed ?");
//		story3.setImageUrl("https://bna-s3.s3.amazonaws.com/img/next.jpg");
//		story3.setUrl("/next");
//		response.getStories().add(story3);
		return response;
	}
}