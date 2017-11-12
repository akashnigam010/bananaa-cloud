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
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.merchant.response.AppItemDetailsResponse;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.persistence.entity.DishCount;

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

	public ItemsResponse map(List<DishDto> dtos, ItemsResponse response) {
		List<Item> items = new ArrayList<>();
		for (DishDto dto : dtos) {
			Item item = new Item();
			item.setId(dto.getId());
			item.setName(dto.getName());
			item.setCost(dto.getCost());
			item.setRating(dto.getRating() != null ? dto.getRating().toString() : "");
			item.setThumbnail(dto.getThumbnail());
			item.setImageUrl(dto.getImageUrl());
			item.setNameId(dto.getNameId());
			item.setRecommendations(dto.getRecommendations().size());
			item.setItemUrl(dto.getItemUrl());
			items.add(item);
		}

		response.setItems(items);
		return response;
	}
	
	public AppItemDetailsResponse mapAppDetailsReponse(DishDto dto) {
		AppItemDetailsResponse response = new AppItemDetailsResponse();
		response.setId(dto.getId());
		response.setName(dto.getName());
		response.setCost(dto.getCost());
		response.setMerchantId(dto.getMerchant().getId());
		response.setMerchantName(dto.getMerchant().getName());
		response.setShortAddress(dto.getMerchant().getAddress().getLocality().getShortAddress());
		response.setRating(dto.getRating() != null ? dto.getRating().toString() : "");
		response.setImageUrl(dto.getImageUrl());
		response.setItemUrl(dto.getItemUrl());
		response.setItemUrlAbsolute(dto.getItemUrlAbsolute());
		response.setTotalRatings(dto.getRecommendationCount());
		return response;
	}
	
	public List<RecommendationDto> mapReviews(List<RecommendationDto> recommendations) {
		List<RecommendationDto> dtos = new ArrayList<>();
		// add only objects where description is not empty
		for (RecommendationDto rcmd : recommendations) {
			if (StringUtils.isNotBlank(rcmd.getDescription())) {
				dtos.add(rcmd);
			}			
		}
		return dtos;
	}

	public List<Integer> getCuisineIds(List<Tag> tags) {
		List<Integer> cuisineIds = new ArrayList<>();
		for (Tag tag : tags){
			cuisineIds.add(tag.getId());
		}
		return cuisineIds;
	}

	public void mapDishCount(List<Tag> tags, List<DishCount> dishCounts) {
		for (Tag tag : tags) {
			for (DishCount dishCount : dishCounts) {
				if (tag.getId() == dishCount.getCuisineId()) {
					tag.setDishCount(dishCount.getCount().intValue());
				}
			}
		}		
	}
}
