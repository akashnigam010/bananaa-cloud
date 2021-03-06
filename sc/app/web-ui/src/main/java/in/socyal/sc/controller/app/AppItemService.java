package in.socyal.sc.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.IdPageRequest;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.merchant.response.AppItemDetailsResponse;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.core.validation.ItemValidator;

@RestController
@RequestMapping(value = "/bna/item")
public class AppItemService {
	@Autowired
	ItemDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	ItemValidator validator;

	@RequestMapping(value = "/getDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	public AppItemDetailsResponse getDetails(@RequestBody IdRequest request) {
		AppItemDetailsResponse response = new AppItemDetailsResponse();
		try {
			validator.validateIdRequest(request);
			response = delegate.getItemDetailsById(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getPopularItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemsResponse getPopularItems(@RequestBody IdPageRequest request) {
		ItemsResponse response = new ItemsResponse();
		try {
			validator.validateIdPageRequest(request);
			TrendingRequest itemsRequest = new TrendingRequest(request.getId(), 10, request.getPage());
			response = delegate.getPopularItems(itemsRequest);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
