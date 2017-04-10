package in.socyal.sc.app.merchant;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.response.GetCuisinesResponse;
import in.socyal.sc.api.manage.response.GetSuggestionsResponse;

public interface ManagementDelegate {
	public void addItem(AddItemRequest request) throws BusinessException;
	
	public void addCuisine(AddRequest request);

	public void addSuggestion(AddRequest request);

	public GetCuisinesResponse getCuisines(SearchRequest request);

	public GetSuggestionsResponse getSuggestions(SearchRequest request);
}
