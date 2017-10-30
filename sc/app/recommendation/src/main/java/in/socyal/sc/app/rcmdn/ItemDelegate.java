package in.socyal.sc.app.rcmdn;

import java.util.List;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.GenericSearchRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.PopularTagResponse;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.items.request.GetFoodSuggestionsRequest;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.merchant.response.AppItemDetailsResponse;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.user.dto.UserTagPreference;

public interface ItemDelegate {
	SearchItemsResponse searchItems(SearchRequest request);

	List<GlobalSearchItem> searchTags(GenericSearchRequest request, TagType tagType, Integer page, Integer resultsPerPage);

	List<UserTagPreference> searchTagsWithUserPrefs(GenericSearchRequest request, TagType tagType, Integer page,
			Integer resultsPerPage) throws BusinessException;

	ItemsResponse getPopularItems(TrendingRequest request) throws BusinessException;

	AppItemDetailsResponse getItemDetailsById(IdRequest request) throws BusinessException;
	
	ItemDetailsResponse getItemDetailsWithFoodviews(DetailsRequest request) throws BusinessException;

	PopularTagResponse getPopularCuisines(LocationCookieDto cookieDto) throws BusinessException;

	PopularTagResponse getPopularDishes(LocationCookieDto cookieDto) throws BusinessException;

	MerchantListForTagResponse searchDishByName(String searchString, LocationCookieDto cookieDto, Integer page)
			throws BusinessException;
	
	MerchantListForTagResponse searchDishByNameForMobile(String searchString, LocationCookieDto cookieDto, Integer page)
			throws BusinessException;

	List<DishDto> getSuggestions(GetFoodSuggestionsRequest request) throws BusinessException;
}
