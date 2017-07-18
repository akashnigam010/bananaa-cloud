package in.socyal.sc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.response.GetTrendingMerchantsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.GlobalSearchResponse;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.MerchantShortDetails;
import in.socyal.sc.api.merchant.response.SearchMerchantResponse;
import in.socyal.sc.api.merchant.response.StoriesResponse;
import in.socyal.sc.api.merchant.response.Story;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.helper.JsonHelper;
import in.socyal.sc.helper.LocalityCookieHelper;

@RestController
@RequestMapping(value = "/socyal/merchant")
public class MerchantService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	MerchantDelegate delegate;
	@Autowired
	ItemDelegate itemDelegate;
	@Autowired
	ResponseHelper responseHelper;
	@Autowired
	LocalityCookieHelper cookieHelper;

	@RequestMapping(value = "/gSearch", method = RequestMethod.POST, headers = "Accept=application/json")
	public GlobalSearchResponse globalSearch(@RequestBody SearchRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/merchant/globalSearch");
		GlobalSearchResponse response = new GlobalSearchResponse();
		try {
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				List<GlobalSearchItem> merchants = delegate.searchMerchantsGlobal(request);
				List<GlobalSearchItem> cuisines = itemDelegate.searchTags(request, TagType.CUISINE);
				List<GlobalSearchItem> suggestions = itemDelegate.searchTags(request, TagType.SUGGESTION);
				response.getSearchItems().addAll(merchants);
				response.getSearchItems().addAll(cuisines);
				response.getSearchItems().addAll(suggestions);
				if (response.getSearchItems().size() == 0) {
					GlobalSearchItem noMatchFound = new GlobalSearchItem(null);
					noMatchFound.setName("No match found");
					response.getSearchItems().add(noMatchFound);
				}
			}
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
				response = delegate.searchActiveMerchant(request);
				if (response.getMerchants().size() == 0) {
					MerchantShortDetails noMatchFound = new MerchantShortDetails();
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
	public GetTrendingMerchantsResponse getTrendingRestaurants(
			@CookieValue(name = "loc", defaultValue = "") String locationCookie) {
		GetTrendingMerchantsResponse response = new GetTrendingMerchantsResponse();
		try {
			LocationCookieDto cookieDto = cookieHelper.getLocalityData(locationCookie);
			response = delegate.getTrendingMerchants(cookieDto);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getMerchantsByTag", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantListForTagResponse getMerchantsByTag(@RequestBody SearchMerchantByTagRequest request) {
		MerchantListForTagResponse response = new MerchantListForTagResponse();
		try {
			response = delegate.getMerchantsByTag(request);
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

	private StoriesResponse createStories() {
		StoriesResponse response = new StoriesResponse();
		Story story1 = new Story();
		story1.setName("Bananaa - What the Fuzz!");
		story1.setImageUrl("https://bna-s3.s3.amazonaws.com/img/what-mini.jpg");
		story1.setUrl("/about");
		response.getStories().add(story1);
		Story story2 = new Story();
		story2.setName("Food Suggestions. But wait..how ?");
		story2.setImageUrl("https://bna-s3.s3.amazonaws.com/img/next.jpg");
		story2.setUrl("/how");
		response.getStories().add(story2);
		// Story story3 = new Story();
		// story3.setName("Where are we headed ?");
		// story3.setImageUrl("https://bna-s3.s3.amazonaws.com/img/next.jpg");
		// story3.setUrl("/next");
		// response.getStories().add(story3);
		return response;
	}
}