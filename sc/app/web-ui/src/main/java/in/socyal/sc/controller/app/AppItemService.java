package in.socyal.sc.controller.app;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.SearchTagResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.core.validation.ItemValidator;

@RestController
@RequestMapping(value = "/bna/item")
public class AppItemService {
	private static final Logger LOG = Logger.getLogger(AppItemService.class);
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;
	@Autowired
	ResponseHelper helper;
	@Autowired
	ItemDelegate delegate;
	@Autowired
	ItemValidator validator;

	@RequestMapping(value = "/searchCuisine", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchTagResponse searchCuisine(@RequestBody SearchRequest request) {
		return searchTag(request, TagType.CUISINE);
	}

	@RequestMapping(value = "/searchSuggestion", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchTagResponse searchSuggestion(@RequestBody SearchRequest request) {
		return searchTag(request, TagType.SUGGESTION);
	}

	private SearchTagResponse searchTag(SearchRequest request, TagType tagType) {
		SearchTagResponse response = new SearchTagResponse();
		try {
			validator.validateSearchTagRequest(request);
			if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
				List<GlobalSearchItem> cuisines = delegate.searchTags(request, tagType, 1, 7);
				response.setSearchItems(cuisines);
			}
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}
}
