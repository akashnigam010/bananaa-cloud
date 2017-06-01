package in.socyal.sc.app.rcmdn.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.item.response.Item;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.SearchItem;
import in.socyal.sc.api.items.dto.DishResultDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;

@Component
public class ItemMapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<SearchItem> map(List<DishDto> dtos) {
		List<SearchItem> items = new ArrayList<>();
		SearchItem item = null;
		for (DishDto dto : dtos) {
			item = new SearchItem();
			item.setId(dto.getId());
			item.setName(dto.getName());
			item.setMerchantName(dto.getMerchant().getName());
			items.add(item);
		}
		return items;
	}

	public ItemsResponse map(List<DishResultDto> result, ItemsResponse response) {
		List<Item> items = new ArrayList<>();
		for (DishResultDto dto : result) {
			Item item = new Item();
			DishDto dish = dto.getDish();
			item.setId(dish.getId());
			item.setName(dish.getName());
			item.setThumbnail(dish.getThumbnail());
			item.setNameId(dish.getNameId());
			item.setRecommendations(dto.getRecommendations().intValue());
			item.setItemUrl(dto.getDish().getItemUrl());
			items.add(item);
		}

		response.setItems(items);
		return response;
	}
	
	public List<RecommendationDto> mapReviews(List<RecommendationDto> recommendations) {
		List<RecommendationDto> dtos = new ArrayList<>();
		RecommendationDto dto = null;
		for (RecommendationDto rcmd : recommendations) {
			if (StringUtils.isNotBlank(rcmd.getDescription())) {
				dto = new RecommendationDto();
				dto.setId(rcmd.getId());
				dto.setUpdatedDateTime(rcmd.getUpdatedDateTime());
				dto.setTimeDiff(rcmd.getTimeDiff());
				dto.setDescription(rcmd.getDescription());
				dto.setDish(rcmd.getDish());
				dto.setUser(rcmd.getUser());
				dtos.add(dto);
			}			
		}
		return dtos;
	}

}
