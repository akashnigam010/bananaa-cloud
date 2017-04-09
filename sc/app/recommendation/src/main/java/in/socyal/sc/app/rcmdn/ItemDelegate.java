package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.request.GetPopularItemsRequest;

public interface ItemDelegate {
	public ItemsResponse searchItems(SearchRequest request);
	public ItemsResponse getPopularItems(GetPopularItemsRequest request) throws BusinessException;
}
