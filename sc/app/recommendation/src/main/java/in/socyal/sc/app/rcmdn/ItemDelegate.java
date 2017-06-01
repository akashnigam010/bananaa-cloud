package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.items.request.GetPopularItemsRequest;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;

public interface ItemDelegate {
	SearchItemsResponse searchItems(SearchRequest request);
	ItemsResponse getPopularItems(GetPopularItemsRequest request) throws BusinessException;
	ItemDetailsResponse getItemDetails(DetailsRequest request) throws BusinessException;
}
