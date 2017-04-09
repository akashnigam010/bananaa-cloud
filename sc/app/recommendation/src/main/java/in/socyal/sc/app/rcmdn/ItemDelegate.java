package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.item.response.ItemsResponse;

public interface ItemDelegate {
	public ItemsResponse searchItems(SearchRequest request);
}
