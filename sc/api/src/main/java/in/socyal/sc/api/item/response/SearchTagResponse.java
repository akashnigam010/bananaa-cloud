package in.socyal.sc.api.item.response;

import java.util.List;

import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.response.GenericResponse;

public class SearchTagResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<GlobalSearchItem> searchItems;

	public List<GlobalSearchItem> getSearchItems() {
		return searchItems;
	}

	public void setSearchItems(List<GlobalSearchItem> searchItems) {
		this.searchItems = searchItems;
	}
}
