package in.socyal.sc.app.rcmdn.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.item.response.Item;

@Component
public class ItemMapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<Item> map(List<DishDto> dtos) {
		List<Item> items = new ArrayList<>();
		Item item = null;
		for (DishDto dto : dtos) {
			item = new Item();
			item.setId(dto.getId());
			item.setName(dto.getName());
			items.add(item);
		}
		return items;
	}

}
