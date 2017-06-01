package in.socyal.sc.api.item.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class SearchItemsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SearchItem> items;

	public List<SearchItem> getItems() {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<SearchItem> items) {
		this.items = items;
	}
}
