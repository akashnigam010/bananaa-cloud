package in.socyal.sc.app.rcmdn;

import java.util.List;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.PopularTagResponse;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.item.response.TagResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.type.TagType;

public interface ItemDelegate {
	SearchItemsResponse searchItems(SearchRequest request);

	List<GlobalSearchItem> searchTags(SearchRequest request, TagType tagType);

	ItemsResponse getPopularItems(TrendingRequest request) throws BusinessException;

	ItemDetailsResponse getItemDetails(DetailsRequest request) throws BusinessException;

	PopularTagResponse getPopularCuisines() throws BusinessException;

	PopularTagResponse getPopularDishes() throws BusinessException;
}
