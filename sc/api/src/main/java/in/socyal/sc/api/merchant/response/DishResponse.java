package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class DishResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Dish> dishes;

	public List<Dish> getDishes() {
		if (this.dishes == null) {
			this.dishes = new ArrayList<>();
		}
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}
}
