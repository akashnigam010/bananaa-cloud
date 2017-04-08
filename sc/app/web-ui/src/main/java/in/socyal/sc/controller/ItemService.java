package in.socyal.sc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.item.response.Item;
import in.socyal.sc.api.item.response.ItemsResponse;

@RestController
@RequestMapping(value = "/socyal/item")
public class ItemService {
	@Autowired
	ResponseHelper helper;

	@RequestMapping(value = "/searchItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemsResponse searchItems(@RequestBody SearchRequest request) {
		ItemsResponse response = new ItemsResponse();
		if (StringUtils.isNotEmpty(request.getSearchString())) {
			if (request.getSearchString().length() >= 2) {
				/*
				 * FIXME: response.setItems(delegate.searchItems(request));
				 * return top 5 searches only
				 */
				response.setItems(getSearchResults());
			}
		}
		return helper.success(response);
	}

	private List<Item> getSearchResults() {
		List<Item> items = new ArrayList<>();
		Item dish5 = new Item();
		dish5.setId(2);
		dish5.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish5.setName("Lowley Sirley");
		dish5.setNameId("lowley-shirley");
		dish5.setRecommendations(15);
		items.add(dish5);
		Item dish6 = new Item();
		dish6.setId(3);
		dish6.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish6.setName("Arrabiata Pasta");
		dish6.setNameId("arrabiata-pasta");
		dish6.setRecommendations(12);
		items.add(dish6);
		Item dish7 = new Item();
		dish7.setId(4);
		dish7.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish7.setName("Mango Delight Punch");
		dish7.setNameId("mango-delight");
		dish7.setRecommendations(10);
		items.add(dish7);
		Item dish8 = new Item();
		dish8.setId(2);
		dish8.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish8.setName("Lowley Sirley");
		dish8.setNameId("lowley-shirley");
		dish8.setRecommendations(15);
		items.add(dish8);
		Item dish9 = new Item();
		dish9.setId(3);
		dish9.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish9.setName("Arrabiata Pasta");
		dish9.setNameId("arrabiata-pasta");
		dish9.setRecommendations(12);
		items.add(dish9);
		Item dish10 = new Item();
		dish10.setId(4);
		dish10.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish10.setName("Mango Delight Punch");
		dish10.setNameId("mango-delight");
		dish10.setRecommendations(10);
		items.add(dish10);
		return items;
	}
}
