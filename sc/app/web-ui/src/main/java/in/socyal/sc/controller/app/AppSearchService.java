package in.socyal.sc.controller.app;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.location.response.LocalitiesResponse;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.GlobalSearchResponse;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.core.validation.ItemValidator;

@RestController
@RequestMapping(value = "/bna/search")
public class AppSearchService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	MerchantDelegate merchantDelegate;
	@Autowired
	ItemDelegate itemDelegate;
	@Autowired
	ItemValidator validator;
	@Autowired
	ResponseHelper responseHelper;

	@RequestMapping(value = "/gSearch", method = RequestMethod.POST, headers = "Accept=application/json")
	public GlobalSearchResponse globalSearch(@RequestBody GenericSearchRequest request) {
		GlobalSearchResponse response = new GlobalSearchResponse();
		try {
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				List<GlobalSearchItem> merchants = merchantDelegate.searchMerchantsGlobal(request);
				List<GlobalSearchItem> cuisines = itemDelegate.searchTags(request, TagType.CUISINE, 1, 3);
				List<GlobalSearchItem> suggestions = itemDelegate.searchTags(request, TagType.SUGGESTION, 1, 3);
				response.getSearchItems().addAll(merchants);
				response.getSearchItems().addAll(cuisines);
				response.getSearchItems().addAll(suggestions);
			}
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/dishes", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchItemsResponse searchDishes(@RequestBody SearchRequest request) {
		SearchItemsResponse response = new SearchItemsResponse();
		try {
			validator.validateSearchItemsRequest(request);
			if (StringUtils.isNotEmpty(request.getSearchString())) {
				if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
					response = itemDelegate.searchItems(request);
				}
			}
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getPopularLocalities", method = RequestMethod.GET, headers = "Accept=application/json")
	public LocalitiesResponse getPopularLocalities() {
		LocalitiesResponse response = new LocalitiesResponse();
		response.setCities(merchantDelegate.getCities());
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/searchMerchants", method = RequestMethod.POST, headers = "Accept=application/json")
	public MerchantListForTagResponse searchMerchants(@RequestBody SearchMerchantRequest request) {
		MerchantListForTagResponse response = new MerchantListForTagResponse();
		try {
			validator.validateSearchMerchantsRequest(request);
			if (request.getIsTagSearch()) {
				if (request.getType() != null) {
					// tag search where tag is provided
					response = merchantDelegate.getMerchantsByTagId(request);
				} else {
					// no tag => all merchants search
					LocationCookieDto cookieDto = new LocationCookieDto(request.getLocationId(), request.getIsCity(), 
							request.getSearchString());
					response = merchantDelegate.getAllSortedMerchantsForMobile(cookieDto, request.getPage());
				}
			} else {
				if (request.getIsMerchantSearch()) {
					LocationCookieDto cookieDto = new LocationCookieDto(request.getLocationId(), request.getIsCity(), 
							request.getSearchString());
					response = merchantDelegate.getAllSortedMerchantsForMobile(cookieDto, request.getPage());
				} else {
					if (StringUtils.isNotBlank(request.getSearchString())) {
						// not tag search => search for places serving the passed string in their menu
						LocationCookieDto cookieDto = new LocationCookieDto(request.getLocationId(), request.getIsCity(), 
								request.getSearchString());
						response = itemDelegate.searchDishByNameForMobile(request.getSearchString(), cookieDto, request.getPage());
					} else {
						// no tag search and no search string => all merchants search
						LocationCookieDto cookieDto = new LocationCookieDto(request.getLocationId(), request.getIsCity(), 
								request.getSearchString());
						response = merchantDelegate.getAllSortedMerchantsForMobile(cookieDto, request.getPage());
					}
				}
			}
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}
