package in.socyal.sc.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.SearchTagResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.user.request.SavePreferencesRequest;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.app.response.VegnonvegPreferenceResponse;
import in.socyal.sc.core.validation.ItemValidator;
import in.socyal.sc.user.UserDelegate;

@RestController
@RequestMapping(value = "/bna/user")
public class AppUserService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	UserDelegate delegate;
	@Autowired
	ItemDelegate itemDelegate;
	@Autowired
	ItemValidator validator;

	@RequestMapping(value = "/getVegnonvegPreference", method = RequestMethod.POST, headers = "Accept=application/json")
	public VegnonvegPreferenceResponse searchCuisine() {
		VegnonvegPreferenceResponse response = new VegnonvegPreferenceResponse();
		try {
			response.setId(delegate.getVegnonvegPreference());
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/saveUserPreferences", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse saveUserPreferences(@RequestBody SavePreferencesRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			delegate.saveUserPreferences(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/searchCuisineWithUserPrefs", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchTagResponse searchCuisine(@RequestBody GenericSearchRequest request) {
		return searchTag(request, TagType.CUISINE);
	}

	@RequestMapping(value = "/searchSuggestionWithUserPrefs", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchTagResponse searchSuggestion(@RequestBody GenericSearchRequest request) {
		return searchTag(request, TagType.SUGGESTION);
	}

	private SearchTagResponse searchTag(GenericSearchRequest request, TagType tagType) {
		SearchTagResponse response = new SearchTagResponse();
		List<GlobalSearchItem> searchItems;
		try {
			if (request.getPage() == null) {
				request.setPage(1);
			}
			searchItems = itemDelegate.searchTagsWithUserPrefs(request, tagType, request.getPage(), 20);
			response.setSearchItems(searchItems);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}

	}
}
