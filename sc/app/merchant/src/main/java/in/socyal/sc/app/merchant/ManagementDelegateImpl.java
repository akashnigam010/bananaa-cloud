package in.socyal.sc.app.merchant;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRecommendationsRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.request.DishVegnonvegValuesRequest;
import in.socyal.sc.api.manage.request.MessageRequest;
import in.socyal.sc.api.manage.request.UpdateItemRequest;
import in.socyal.sc.api.manage.response.GetAllItemsResponse;
import in.socyal.sc.api.manage.response.GetCuisinesResponse;
import in.socyal.sc.api.manage.response.GetItemImagesResponse;
import in.socyal.sc.api.manage.response.GetSuggestionsResponse;
import in.socyal.sc.api.manage.response.Item;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.helper.mail.MailSender;
import in.socyal.sc.persistence.ManagementDao;
import in.socyal.sc.persistence.cache.BnaCacheManager;
import in.socyal.sc.rating.engine.dish.CuisineRatingEngine;
import in.socyal.sc.rating.engine.dish.DishRatingEngine;
import in.socyal.sc.rating.engine.dish.SuggestionRatingEngine;

@Service
public class ManagementDelegateImpl implements ManagementDelegate {

	@Autowired
	ManagementDao dao;
	@Autowired
	DishRatingEngine dishRatingEngine;
	@Autowired
	CuisineRatingEngine cuisineRatingEngine;
	@Autowired
	SuggestionRatingEngine tagRatingEngine;
	@Autowired
	MailSender mailSender;
	@Autowired
	BnaCacheManager cacheManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addItem(AddItemRequest request) throws BusinessException {
		request.setName(WordUtils.capitalizeFully(request.getName().trim()));
		request.setNameId(createNameId(request.getName()));
		dao.addItem(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateItem(UpdateItemRequest request) throws BusinessException {
		request.setName(WordUtils.capitalizeFully(request.getName().trim()));
		request.setNameId(createNameId(request.getName()));
		dao.updateItem(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteItem(IdRequest request) throws BusinessException {
		dao.deleteItem(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addRecommendations(AddRecommendationsRequest request) throws BusinessException {
		dao.addRecommendations(request.getItemId(), request.getRating(), request.getRcmdCount());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addCuisine(AddRequest request) {
		dao.addCuisine(WordUtils.capitalizeFully(request.getName().trim()));
		cacheManager.refreshCuisinesCache();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addSuggestion(AddRequest request) {
		dao.addSuggestion(WordUtils.capitalizeFully(request.getName().trim()));
		cacheManager.refreshSuggestionsCache();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetCuisinesResponse getCuisines(SearchRequest request) {
		GetCuisinesResponse response = new GetCuisinesResponse();
		response.setCuisines(dao.getCuisines(request));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetSuggestionsResponse getSuggestions(SearchRequest request) {
		GetSuggestionsResponse response = new GetSuggestionsResponse();
		response.setSuggestions(dao.getSuggestions(request));
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetItemImagesResponse getItemImages(SearchRequest request) {
		GetItemImagesResponse response = new GetItemImagesResponse();
		response.setImages(dao.getItemImages(request));
		return response;
	}

	private String createNameId(String name) {
		String[] nameSegments = name.split(" ");
		StringBuilder nameId = new StringBuilder();
		int i;
		for (i = 0; i < nameSegments.length - 1; i++) {
			nameId.append(nameSegments[i].toLowerCase() + "-");
		}
		nameId.append(nameSegments[i].toLowerCase());
		return nameId.toString();
	}

	@Override
	public void contactUsMessage(MessageRequest request) throws BusinessException {
		mailSender.sendEmail(request);
	}

	@Override
	public StatusResponse runDishRatingEngineForMerchant(IdRequest request) {
		dishRatingEngine.rateRestaurantDishes(request.getId());
		return new StatusResponse();
	}

	@Override
	public StatusResponse runCuisineRatingEngineForMerchant(IdRequest request) {
		cuisineRatingEngine.rateRestaurantForCuisines(request.getId());
		return new StatusResponse();
	}

	@Override
	public StatusResponse runTagsRatingEngineForMerchant(IdRequest request) {
		tagRatingEngine.rateRestaurantForTags(request.getId());
		return new StatusResponse();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetAllItemsResponse getAllItems(IdRequest request) throws BusinessException {
		GetAllItemsResponse response = new GetAllItemsResponse();
		List<Item> dishes = dao.getAllItems(request);
		for (Item item : dishes) {
			response.getDishes().put(item.getId(), item);
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateDishVegnonvegValues(DishVegnonvegValuesRequest request) throws BusinessException {
		dao.updateDishVegnonvegValues(request);
	}
}
