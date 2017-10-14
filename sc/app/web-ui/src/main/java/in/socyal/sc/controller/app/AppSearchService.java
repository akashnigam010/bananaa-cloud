package in.socyal.sc.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.GlobalSearchResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.controller.MerchantService;
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
	ResponseHelper responseHelper;

	@RequestMapping(value = "/gSearch", method = RequestMethod.POST, headers = "Accept=application/json")
	public GlobalSearchResponse globalSearch(@RequestBody SearchRequest request) {
		JsonHelper.logRequest(request, MerchantService.class, "/search/globalSearch");
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
}
