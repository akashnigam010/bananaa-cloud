package in.socyal.sc.api.item.response;

import java.util.List;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.response.GenericResponse;

public class GetFoodSuggestionsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	List<DishDto> dishes;

	public List<DishDto> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishDto> dishes) {
		this.dishes = dishes;
	}
}
