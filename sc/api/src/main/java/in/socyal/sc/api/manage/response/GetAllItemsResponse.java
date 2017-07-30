package in.socyal.sc.api.manage.response;

import java.util.HashMap;
import java.util.Map;

import in.socyal.sc.api.response.GenericResponse;

public class GetAllItemsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private Map<Integer, Item> dishes;

	public Map<Integer, Item> getDishes() {
		if (this.dishes == null) {
			this.dishes = new HashMap<>();
		}
		return dishes;
	}

	public void setDishes(Map<Integer, Item> dishes) {
		this.dishes = dishes;
	}

}
