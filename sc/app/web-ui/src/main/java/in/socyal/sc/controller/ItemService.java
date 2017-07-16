package in.socyal.sc.controller;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.PopularTagResponse;
import in.socyal.sc.api.item.response.SearchItem;
import in.socyal.sc.api.item.response.SearchItemsResponse;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.core.validation.ItemValidator;
import in.socyal.sc.helper.LocalityCookieDto;
import in.socyal.sc.helper.LocalityCookieHelper;

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
	@Autowired
	LocalityCookieHelper cookieHelper;

	@RequestMapping(value = "/searchItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchItemsResponse searchItems(@RequestBody SearchRequest request) {
		SearchItemsResponse response = new SearchItemsResponse();
		try {
			validator.validateSearchItemsRequest(request);
			if (StringUtils.isNotEmpty(request.getSearchString())) {
				if (request.getSearchString().length() >= 2) {
					response = delegate.searchItems(request);
					if (response.getItems().size() == 0) {
						SearchItem noMatchFound = new SearchItem();
						noMatchFound.setId(-999);
						noMatchFound.setName("No such item found");
						response.getItems().add(noMatchFound);
					}
				}
			}
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getPopularItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemsResponse getPopularItems(@RequestBody TrendingRequest request) {
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

	@RequestMapping(value = "/getTrendingCuisines", method = RequestMethod.GET, headers = "Accept=application/json")
	public PopularTagResponse getTrendingCuisines(@CookieValue(name = "loc", defaultValue = "") String locationCookie) {
		PopularTagResponse response = new PopularTagResponse();
		try {
			LocalityCookieDto cookieDto = cookieHelper.getLocalityData(locationCookie);
			response = delegate.getPopularCuisines(cookieDto.isCitySearch(), cookieDto.getLocalityId());
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getTrendingDishes", method = RequestMethod.GET, headers = "Accept=application/json")
	public PopularTagResponse getTrendingDishes(@CookieValue(name = "loc", defaultValue = "") String locationCookie) {
		PopularTagResponse response = new PopularTagResponse();
		try {
			LocalityCookieDto cookieDto = cookieHelper.getLocalityData(locationCookie);
			response = delegate.getPopularDishes(cookieDto.isCitySearch(), cookieDto.getLocalityId());
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}
}
