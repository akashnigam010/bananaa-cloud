package in.socyal.sc.app.rcmdn.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.item.response.Item;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.dto.DishResultDto;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.Review;
import in.socyal.sc.api.merchant.response.User;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.UserDto;

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

	public ItemsResponse map(List<DishResultDto> result, ItemsResponse response) {
		List<Item> items = new ArrayList<>();
		for (DishResultDto dto : result) {
			Item item = new Item();
			DishDto dish = dto.getDish();
			item.setId(dish.getId());
			item.setName(dish.getName());
			item.setImageUrl(dish.getImageUrl());
			item.setNameId(dish.getNameId());
			item.setRecommendations(dto.getRecommendations().intValue());
			item.setItemUrl(dto.getDish().getItemUrl());
			items.add(item);
		}

		response.setItems(items);
		return response;
	}

	public ItemDetailsResponse map(DishResultDto dishResult, List<RecommendationDto> rcmdns) {
		ItemDetailsResponse response = new ItemDetailsResponse();
		DishDto dish = dishResult.getDish();
		response.setId(dish.getId());
		response.setImageUrl(dish.getImageUrl());
		response.setItemUrl(dish.getItemUrl());
		response.setMerchantName(dish.getMerchant().getName());
		response.setMerchantShortAddress(dish.getMerchant().getAddress().getLocality().getShortAddress());
		response.setMerchantUrl(dish.getMerchant().getMerchantUrl());
		response.setName(dish.getName());
		response.setRecommendations(dishResult.getRecommendations().intValue());
		List<Review> reviews = new ArrayList<>();
		for (RecommendationDto rcmdn : rcmdns) {
			Review review = new Review();
			review.setDate(rcmdn.getCreatedDateTime());
			review.setDescription(rcmdn.getDescription());
			review.setUser(map(rcmdn.getUser()));
			reviews.add(review);
		}
		response.setReviews(reviews);
		return response;
	}
	
	public User map(UserDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setImageUrl(dto.getImageUrl());
		user.setUserUrl(null);
		return user;
	}
}
