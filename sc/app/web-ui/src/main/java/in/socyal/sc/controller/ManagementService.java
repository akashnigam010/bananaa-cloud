package in.socyal.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRecommendationsRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.manage.request.MessageRequest;
import in.socyal.sc.api.manage.response.AddResponse;
import in.socyal.sc.api.manage.response.GetCuisinesResponse;
import in.socyal.sc.api.manage.response.GetItemImagesResponse;
import in.socyal.sc.api.manage.response.GetSuggestionsResponse;
import in.socyal.sc.api.response.StatusResponse;
import in.socyal.sc.app.merchant.ManagementDelegate;
import in.socyal.sc.core.validation.ManageValidator;

@RestController
@RequestMapping(value = "/socyal/management")
public class ManagementService {
	public static final Integer MINIMUM_SEARCH_STRING_LENGTH = 2;

	@Autowired
	ResponseHelper helper;
	@Autowired
	ManagementDelegate delegate;
	@Autowired
	ManageValidator validator;
	
	@RequestMapping(value = "/addItem", method = RequestMethod.POST, headers = "Accept=application/json")
	public AddResponse addItem(@RequestBody AddItemRequest request) {
		AddResponse response = new AddResponse();
		try {
			validator.validateAddItemRequest(request);
			delegate.addItem(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/addRecommendations", method = RequestMethod.POST, headers = "Accept=application/json")
	public AddResponse addRecommendations(@RequestBody AddRecommendationsRequest request) {
		AddResponse response = new AddResponse();
		try {
			validator.validateAddRecommendationsRequest(request);
			delegate.addRecommendations(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addCuisine", method = RequestMethod.POST, headers = "Accept=application/json")
	public AddResponse addCuisine(@RequestBody AddRequest request) {
		AddResponse response = new AddResponse();
		try {
			validator.validateAddRequest(request);
			delegate.addCuisine(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addSuggestion", method = RequestMethod.POST, headers = "Accept=application/json")
	public AddResponse addSuggestion(@RequestBody AddRequest request) {
		AddResponse response = new AddResponse();
		try {
			validator.validateAddRequest(request);
			delegate.addSuggestion(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getCuisines", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetCuisinesResponse getCuisines(@RequestBody SearchRequest request) {
		GetCuisinesResponse response = new GetCuisinesResponse();
		if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
			response = delegate.getCuisines(request);
		}
		return helper.success(response);
	}

	@RequestMapping(value = "/getSuggestions", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetSuggestionsResponse getSuggestions(@RequestBody SearchRequest request) {
		GetSuggestionsResponse response = new GetSuggestionsResponse();
		if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
			response = delegate.getSuggestions(request);
		}
		return helper.success(response);
	}
	
	@RequestMapping(value = "/getItemImages", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetItemImagesResponse getItemImages(@RequestBody SearchRequest request) {
		GetItemImagesResponse response = new GetItemImagesResponse();
		if (request.getSearchString().length() >= MINIMUM_SEARCH_STRING_LENGTH) {
			response = delegate.getItemImages(request);
		}
		return helper.success(response);
	}
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse sendMessage(@RequestBody MessageRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateSendMessageRequest(request);
			delegate.contactUsMessage(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}		
	}
}
