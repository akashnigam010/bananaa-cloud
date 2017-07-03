package in.socyal.sc.api.merchant.response;

import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class GlobalSearchResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<GlobalSearchItem> searchItems;

	public List<GlobalSearchItem> getSearchItems() {
		if (this.searchItems == null) {
			this.searchItems = new ArrayList<>();
		}
		return searchItems;
	}

	public void setSearchItems(List<GlobalSearchItem> searchItems) {
		this.searchItems = searchItems;
	}
}
