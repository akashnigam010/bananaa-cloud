package in.socyal.sc.api.item.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class ItemsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Item> items;

	public List<Item> getItems() {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
