package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class PopularItemsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Dish> items;

	public List<Dish> getItems() {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<Dish> items) {
		this.items = items;
	}
}
