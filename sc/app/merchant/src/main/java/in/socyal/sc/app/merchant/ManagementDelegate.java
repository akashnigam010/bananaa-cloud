package in.socyal.sc.app.merchant;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRecommendationsRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.request.MessageRequest;
import in.socyal.sc.api.manage.response.GetCuisinesResponse;
import in.socyal.sc.api.manage.response.GetItemImagesResponse;
import in.socyal.sc.api.manage.response.GetSuggestionsResponse;
import in.socyal.sc.api.response.StatusResponse;

public interface ManagementDelegate {
	public void addItem(AddItemRequest request) throws BusinessException;

	public void addRecommendations(AddRecommendationsRequest request) throws BusinessException;

	public void addCuisine(AddRequest request);

	public void addSuggestion(AddRequest request);

	public GetCuisinesResponse getCuisines(SearchRequest request);

	public GetSuggestionsResponse getSuggestions(SearchRequest request);

	public GetItemImagesResponse getItemImages(SearchRequest request);

	public void contactUsMessage(MessageRequest request) throws BusinessException;

	StatusResponse runDishRatingEngineForMerchant(IdRequest request);
	
	StatusResponse runCuisineRatingEngineForMerchant(IdRequest request);
	
	StatusResponse runTagsRatingEngineForMerchant(IdRequest request);
}
