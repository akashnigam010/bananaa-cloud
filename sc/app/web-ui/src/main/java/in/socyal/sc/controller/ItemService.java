package in.socyal.sc.controller;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.request.GetPopularItemsRequest;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.core.validation.ItemValidator;

@RestController
@RequestMapping(value = "/socyal/item")
public class ItemService {
	private static final Logger LOG = Logger.getLogger(ItemService.class);
	@Autowired
	ResponseHelper helper;
	@Autowired
	ItemDelegate delegate;
	@Autowired
	ItemValidator validator;

	@RequestMapping(value = "/searchItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemsResponse searchItems(@RequestBody SearchRequest request) {
		ItemsResponse response = new ItemsResponse();
		try {
			validator.validateSearchItemsRequest(request);
			if (StringUtils.isNotEmpty(request.getSearchString())) {
				if (request.getSearchString().length() >= 2) {
					response = delegate.searchItems(request);
				}
			}
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getPopularItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemsResponse getPopularItems(@RequestBody GetPopularItemsRequest request) {
		ItemsResponse response = new ItemsResponse();
		try {
			validator.validateGetPopularItemsRequest(request);
			response = delegate.getPopularItems(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}
}
