package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.item.response.TagResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;

public interface ItemDelegate {
	SearchItemsResponse searchItems(SearchRequest request);
	
	ItemsResponse getPopularItems(TrendingRequest request) throws BusinessException;
	
	ItemDetailsResponse getItemDetails(DetailsRequest request) throws BusinessException;
	
	/**
	 * Popular rated cuisines have now been moved to MerchantDto and would be
	 * mapped based on filter criteria.
	 * 
	 */
	@Deprecated
	TagResponse getPopularCuisines(TrendingRequest request) throws BusinessException;
	
	/**
	 * Popular rated cuisines have now been moved to MerchantDto and would be
	 * mapped based on filter criteria.
	 * 
	 */
	@Deprecated
	TagResponse getPopularSuggestions(TrendingRequest request) throws BusinessException;
}
