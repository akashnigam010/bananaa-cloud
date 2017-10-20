package in.socyal.sc.controller.app;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.GlobalSearchResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.core.validation.ItemValidator;
import in.socyal.sc.helper.JsonHelper;

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
		JsonHelper.logRequest(request, AppSearchService.class, "/search/globalSearch");
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
}
