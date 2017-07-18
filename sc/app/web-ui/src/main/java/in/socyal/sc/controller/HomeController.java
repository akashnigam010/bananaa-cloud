package in.socyal.sc.controller;

import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.login.response.LoginStatus;
import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.cache.CityCache;
import in.socyal.sc.cache.CuisineCache;
import in.socyal.sc.cache.LocalityCache;
import in.socyal.sc.cache.SuggestionCache;
import in.socyal.sc.helper.LocalityCookieHelper;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.user.UserDelegate;

@Controller
public class HomeController {
	private static final Logger LOG = Logger.getLogger(HomeController.class);
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String HOME_URL = "home.url";
	private static final String HOME_TITLE = "home.title";
	private static final String HOME_DESCRIPTION = "home.description";
	private static final String HOME_IMAGE = "home.image";
	private static final String DETAIL_DESCRIPTION_1 = "detail.description.1";
	private static final String DETAIL_DESCRIPTION_2 = "detail.description.2";
	private static final String DETAIL_TITLE_END = "detail.title.end";
	private static final String ITEM_DETAIL_DESCRIPTION_1 = "item.detail.description.1";
	private static final String ITEM_DETAIL_DESCRIPTION_2 = "item.detail.description.2";
	private static final String ITEM_DETAIL_TITLE_END = "item.detail.title.end";
	private static final String USER_DETAIL_DESCRIPTION_1 = "user.detail.description.1";
	private static final String USER_DETAIL_DESCRIPTION_2 = "user.detail.description.2";
	private static final String USER_DETAIL_TITLE_END = "user.detail.title.end";
	private static final String ALL = "all";
	private static final String RESTAURANTS = "restaurants";

	@Autowired
	MerchantDelegate merchantDelegate;
	@Autowired
	ItemDelegate itemDelegate;
	@Autowired
	UserDelegate userDelegate;
	@Autowired
	HttpServletResponse httpResponse;
	@Autowired
	ResponseHelper responseHelper;
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	CityCache cityCache;
	@Autowired
	LocalityCache localityCache;
	@Autowired
	CuisineCache cuisineCache;
	@Autowired
	SuggestionCache suggestionCache;
	@Autowired
	LocalityCookieHelper cookieHelper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@CookieValue(name = "city", defaultValue = "") String city,
			@CookieValue(name = "loc", defaultValue = "") String loc) {
		if (StringUtils.isEmpty(city)) {
			Cookie cityCookie = new Cookie("city", cityCache.getCity("hyderabad").getNameId());
			cityCookie.setPath("/");
			httpResponse.addCookie(cityCookie);
		}
		return "redirect:hyderabad";
	}

	@RequestMapping(value = "/{city}", method = RequestMethod.GET)
	public ModelAndView cityHome(
			@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String locationCookie, 
			@PathVariable("city") String city,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false) Integer page)
			throws BusinessException {
		String[] urlParams = { city };
		ModelAndView modelAndView = initializeModelAndView(bnaLoginCookie, locationCookie, urlParams);
		if (StringUtils.isNotBlank(search)) {
			return globalSearch(modelAndView, locationCookie, search, page);
		}
		modelAndView.setViewName("index");
		modelAndView.addObject("description", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("fbDescription", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("title", resource.getString(HOME_TITLE));
		modelAndView.addObject("imageUrl", resource.getString(HOME_IMAGE));
		modelAndView.addObject("isSearch", false);
		return modelAndView;
	}

	/**
	 * Accepted URLS :<br>
	 * - bananaa.in/city/restaurant <br>
	 * - bananaa.in/city/locality - bananaa.in/city/tag
	 * 
	 * @param bnaLoginCookie
	 * @param locationCookie
	 * @param city
	 * @param restLocalityOrTagNameId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/{city}/{restLocalityOrTagNameId}", method = RequestMethod.GET)
	public ModelAndView restaurantDetailsOrListInALocality(
			@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String locationCookie,
			@PathVariable("restLocalityOrTagNameId") String restLocalityOrTagNameId,
			@PathVariable("city") String city, 
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false) Integer page)
			throws BusinessException {
		String[] urlParams = { city, restLocalityOrTagNameId };
		ModelAndView modelAndView = initializeModelAndView(bnaLoginCookie, locationCookie, urlParams);
		if (StringUtils.isNotBlank(search)) {
			return globalSearch(modelAndView, locationCookie, search, page);
		}
		if (restLocalityOrTagNameId.equalsIgnoreCase(RESTAURANTS)) {
			return allPlacesSearch(modelAndView, locationCookie, page);
		}
		LocalityDto locality = localityCache.getLocality(restLocalityOrTagNameId);
		if (locality != null) {
			// it will never be invoked with normal flow - only via direct url hit
			return allPlacesSearch(modelAndView, locationCookie, page);
		} else {
			CuisineDto cuisine = cuisineCache.getCuisine(restLocalityOrTagNameId);
			if (cuisine != null) {
				TagSearch tagSearch = new TagSearch(modelAndView, page, true, city, null);
				return getTagSearchResults(tagSearch, cuisine);
			} else {
				SuggestionDto suggestion = suggestionCache.getCuisine(restLocalityOrTagNameId);
				if (suggestion != null) {
					TagSearch tagSearch = new TagSearch(modelAndView, page, true, city, null);
					return getTagSearchResults(tagSearch, suggestion);
				} else {
					DetailsRequest request = new DetailsRequest(restLocalityOrTagNameId);
					MerchantDetails response = null;
					try {
						response = merchantDelegate.getMerchantDetails(request);
					} catch (BusinessException e) {
						// Merchant Details not found
						return globalSearch(modelAndView, locationCookie, restLocalityOrTagNameId, page);
					}
					return getMerchantDetails(modelAndView, response);
				}
			}
		}
	}
	
	/**
	 * Accepted URLS :<br>
	 * - bananaa.in/city/restaurant/item <br>
	 * - bananaa.in/city/locality/tag
	 * 
	 * @param bnaLoginCookie
	 * @param locationCookie
	 * @param city
	 * @param restaurantOrLocalityNameId
	 * @param itemOrTagNameId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/{city}/{restaurantOrLocalityNameId}/{itemOrTagNameId}", method = RequestMethod.GET)
	public ModelAndView foodDetails(
			@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String locationCookie, 
			@PathVariable("city") String city,
			@PathVariable("restaurantOrLocalityNameId") String restaurantOrLocalityNameId,
			@PathVariable("itemOrTagNameId") String itemOrTagNameId,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false) Integer page) throws BusinessException {
		String[] urlParams = { city, restaurantOrLocalityNameId, itemOrTagNameId };
		ModelAndView modelAndView = initializeModelAndView(bnaLoginCookie, locationCookie, urlParams);
		if (StringUtils.isNotBlank(search)) {
			return globalSearch(modelAndView, locationCookie, search, page);
		}
		LocalityDto locality = localityCache.getLocality(restaurantOrLocalityNameId);
		if (locality != null) {
			if (itemOrTagNameId.equalsIgnoreCase(RESTAURANTS)) {
				return allPlacesSearch(modelAndView, locationCookie, page);
			}
			CuisineDto cuisine = cuisineCache.getCuisine(itemOrTagNameId);
			if (cuisine != null) {
				TagSearch tagSearch = new TagSearch(modelAndView, page, false, city, locality.getNameId());
				return getTagSearchResults(tagSearch, cuisine);
			} else {
				SuggestionDto suggestion = suggestionCache.getCuisine(itemOrTagNameId);
				if (suggestion != null) {
					TagSearch tagSearch = new TagSearch(modelAndView, page, false, city, locality.getNameId());
					return getTagSearchResults(tagSearch, suggestion);
				} else {
					return globalSearch(modelAndView, locationCookie, itemOrTagNameId, page);
				}
			}
		} else {
			return getItemDetails(modelAndView, itemOrTagNameId, restaurantOrLocalityNameId);
		}
	}

	@RequestMapping(value = "/user/{userNameId}", method = RequestMethod.GET)
	public ModelAndView userDetails(
			@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String locationCookie, 
			@PathVariable("userNameId") String userNameId,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false) Integer page)
			throws BusinessException {
		String[] urlParams = { "user", userNameId };
		ModelAndView modelAndView = initializeModelAndView(bnaLoginCookie, locationCookie, urlParams);
		if (StringUtils.isNotBlank(search)) {
			return globalSearch(modelAndView, locationCookie, search, page);
		}
		modelAndView.setViewName("user-detail");
		setCommonModelData(modelAndView, bnaLoginCookie, locationCookie);
		UserDetailsResponse response = userDelegate.getUserDetails(userNameId);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("description", getUserMetaDescription(response));
		modelAndView.addObject("fbDescription", getUserMetaDescription(response));
		modelAndView.addObject("title", getUserMetaTitle(response));
		modelAndView.addObject("imageUrl", response.getUser().getImageUrl());
		return modelAndView;
	}
	
	private ModelAndView initializeModelAndView(String bnaLoginCookie, String locationCookie, String[] urlParams) {
		ModelAndView modelAndView = new ModelAndView();
		setCommonModelData(modelAndView, bnaLoginCookie, locationCookie);
		modelAndView.addObject("url", getMetaUrl(urlParams));
		return modelAndView;
	}
	
	private ModelAndView globalSearch(ModelAndView modelAndView, String locationCookie, String search,
			Integer page) throws BusinessException {
		modelAndView.setViewName("tag-search");
		page = page == null ? 1 : page;
		LocationCookieDto cookieDto = cookieHelper.getLocalityData(locationCookie);
		if (search.equalsIgnoreCase(ALL)) {
			return allSearch(cookieDto);
		}
		MerchantListForTagResponse response = itemDelegate.searchDishByName(search, cookieDto, page);
		response.setTagName(search);
		response.setLocation(cookieDto.getLocationName());
		modelAndView.addObject("detail", response);
		modelAndView.addObject("title", getSearchDescription(search, cookieDto.getLocationName()));
		modelAndView.addObject("description", getSearchDescription(search, cookieDto.getLocationName()));
		modelAndView.addObject("fbDescription", getSearchDescription(search, cookieDto.getLocationName()));
		modelAndView.addObject("isSearch", true);
		return modelAndView;
	}
	
	private ModelAndView allSearch(LocationCookieDto locationDto) {
		ModelAndView modelAndView = new ModelAndView();
		if (locationDto.isCitySearch()) {
			modelAndView.setViewName("redirect:" + "/" + locationDto.getCityId() + "/" + RESTAURANTS);
		} else {
			modelAndView.setViewName("redirect:" + "/" + locationDto.getCityId() + "/" + locationDto.getLocalityId()
					+ "/" + RESTAURANTS);
		}
		return modelAndView;
	}
	
	private ModelAndView allPlacesSearch(ModelAndView modelAndView, String locationCookie, Integer page)
			throws BusinessException {
		modelAndView.setViewName("tag-search");
		page = page == null ? 1 : page;
		LocationCookieDto cookieDto = cookieHelper.getLocalityData(locationCookie);
		MerchantListForTagResponse response = merchantDelegate.getAllSortedMerchants(cookieDto, page);
		response.setTagName("All places");
		response.setLocation(cookieDto.getLocationName());
		modelAndView.addObject("detail", response);
		modelAndView.addObject("title", getSearchDescription("All places", cookieDto.getLocationName()));
		modelAndView.addObject("description", getSearchDescription("All places", cookieDto.getLocationName()));
		modelAndView.addObject("fbDescription", getSearchDescription("All places", cookieDto.getLocationName()));
		modelAndView.addObject("isSearch", true);
		return modelAndView;
	}
	
	/**
	 * Mapping model and view details for merchant details
	 * @param modelAndView
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	private ModelAndView getMerchantDetails(ModelAndView modelAndView, MerchantDetails response)
			throws BusinessException {
		modelAndView.setViewName("detail");
		TrendingRequest itemsRequest = new TrendingRequest(response.getId(), 5, 1);
		ItemsResponse itemsResponse = itemDelegate.getPopularItems(itemsRequest);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("popularDishes", itemsResponse);
		modelAndView.addObject("popularCuisines", response.getRatedCuisines());
		modelAndView.addObject("description", getMerchantMetaDescription(response));
		modelAndView.addObject("fbDescription", getMerchantMetaDescription(response));
		modelAndView.addObject("title", getMerchantMetaTitle(response));
		modelAndView.addObject("imageUrl", response.getThumbnail());
		return modelAndView;
	}
	
	/**
	 * bananaa.in/hyderabad/fusion-9-hitech-city/greek-pizza
	 * @param modelAndView
	 * @param itemNameId
	 * @param merchantNameId
	 * @param urlParams
	 * @return
	 * @throws BusinessException
	 */
	private ModelAndView getItemDetails(ModelAndView modelAndView, String itemNameId, String merchantNameId)
			throws BusinessException {
		modelAndView.setViewName("item-detail");
		DetailsRequest detailsRequest = new DetailsRequest();
		detailsRequest.setItemNameId(itemNameId);
		detailsRequest.setMerchantNameId(merchantNameId);
		ItemDetailsResponse response = itemDelegate.getItemDetails(detailsRequest);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("description", getItemMetaDescription(response));
		modelAndView.addObject("fbDescription", getItemMetaDescription(response));
		modelAndView.addObject("title", getItemMetaTitle(response));
		modelAndView.addObject("imageUrl", response.getDish().getThumbnail());
		return modelAndView;
	}
	
	/**
	 * bananaa.in/hyderabad/cuisine
	 * bananaa.in/hyderabad/suggestion
	 * bananaa.in/hyderabad/cafe
	 * @param modelAndView
	 * @param tag
	 * @param city
	 * @param tagNameId
	 * @param page
	 * @return
	 * @throws BusinessException
	 */
	private ModelAndView getTagSearchResults(TagSearch tagSearch, Tag tag) throws BusinessException {
		String tagName = tag.getName();
		String location = null;
		ModelAndView modelAndView = tagSearch.getModelAndView();
		modelAndView.setViewName("tag-search");
		SearchMerchantByTagRequest request = new SearchMerchantByTagRequest();
		request.setNameId(tag.getNameId());
		request.setPage(tagSearch.getPage());
		request.setType(tag.getTagType());
		if (tagSearch.isCitySearch()) {
			request.setCityNameId(tagSearch.getCity());
			location = getLocation(tagSearch.getCity(), null);
		} else {
			request.setCityNameId(tagSearch.getCity());
			request.setLocalityNameId(tagSearch.getLocality());
			location = getLocation(tagSearch.getCity(), tagSearch.getLocality());
		}
		
		MerchantListForTagResponse response = merchantDelegate.getMerchantsByTag(request);
		modelAndView.addObject("detail", response);
		String metaDescription = getTagMetaDescription(tagName, location);
		response.setLocation(location);
		response.setTagName(tagName);
		modelAndView.addObject("description", metaDescription);
		modelAndView.addObject("fbDescription", metaDescription);
		modelAndView.addObject("title", tagName + " in " + location);
		modelAndView.addObject("imageUrl", tag.getImageUrl());
		return modelAndView;
	}

	private void setCommonModelData(ModelAndView modelAndView, String bnaLoginCookie, String locationCookie) {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		LocalityDto localityDto = localityHandler(locationCookie);
		if (localityDto == null) {
			CityDto cityDto = cityHandler(locationCookie);
			modelAndView.addObject("location", cityDto.getName());
		} else {
			modelAndView.addObject("location", localityDto.getName());
		}
		modelAndView.addObject("loginStatus", loginStatus);
	}

	private LoginStatus loginHandler(String bnaLoginCookie) {
		jwtHelper.setAuthUser(bnaLoginCookie);
		LoginStatus loginStatus = new LoginStatus();
		if (jwtHelper.isUserLoggedIn()) {
			loginStatus.setStatus(true);
			try {
				loginStatus.setFirstName(jwtHelper.getFirstName());
				loginStatus.setNameId(jwtHelper.getNameId());
			} catch (BusinessException e) {
				LOG.debug("Firstname not found even after checking the login status");
				loginStatus.setStatus(false);
			}
		}
		return loginStatus;
	}

	private CityDto cityHandler(String cityNameId) {
		CityDto city = cityCache.getCity(cityNameId);
		if (city == null) {
			// TODO: fix default city - change in login service too
			city = cityCache.getCity("hyderabad");
		}
		return city;
	}

	private LocalityDto localityHandler(String localityNameId) {
		LocalityDto locality = localityCache.getLocality(localityNameId);		
		return locality;
	}

	private String getMetaUrl(String[] urlParams) {
		String url = resource.getString(HOME_URL);
		for (String urlParam : urlParams) {
			url += "/" + urlParam;
		}
		return url;
	}

	private String getMerchantMetaDescription(MerchantDetails response) {
		String description = response.getName() + "; ";
		description += response.getName() + ", " + response.getShortAddress() + "; ";
		description += resource.getString(DETAIL_DESCRIPTION_1);
		description += " " + response.getName() + " ";
		description += resource.getString(DETAIL_DESCRIPTION_2);
		return description;
	}

	private String getMerchantMetaTitle(MerchantDetails response) {
		String title = response.getName() + ", " + response.getShortAddress() + " ";
		title += resource.getString(DETAIL_TITLE_END);
		return title;
	}

	private String getItemMetaDescription(ItemDetailsResponse response) {
		String description = response.getDish().getName() + "; ";
		description += response.getDish().getName() + " @ " + response.getDish().getMerchant().getName() + "; ";
		description += resource.getString(ITEM_DETAIL_DESCRIPTION_1);
		description += " " + response.getDish().getName() + " - ";
		description += resource.getString(ITEM_DETAIL_DESCRIPTION_2);
		return description;
	}

	private String getItemMetaTitle(ItemDetailsResponse response) {
		String title = response.getDish().getName() + " @ " + response.getDish().getMerchant().getName() + " ";
		title += resource.getString(ITEM_DETAIL_TITLE_END);
		return title;
	}

	private String getUserMetaDescription(UserDetailsResponse response) {
		String description = response.getUser().getName() + "; ";
		description += resource.getString(USER_DETAIL_DESCRIPTION_1);
		description += " " + response.getUser().getName() + " ";
		description += resource.getString(USER_DETAIL_DESCRIPTION_2);
		return description;
	}

	private String getUserMetaTitle(UserDetailsResponse response) {
		String title = response.getUser().getName() + " @ ";
		title += resource.getString(USER_DETAIL_TITLE_END);
		return title;
	}

	private String getLocation(String cityNameId, String localityNameId) {
		CityDto city = cityCache.getCity(cityNameId);
		StringBuilder location = new StringBuilder();
		if (localityNameId != null) {
			LocalityDto locality = localityCache.getLocality(localityNameId); 
			location.append(locality.getName());
			location.append(", ");
		}
		location.append(city.getName());
		return location.toString();
	}

	private String getTagMetaDescription(String tagName, String location) {
		StringBuilder description = new StringBuilder();

		description.append(tagName);
		description.append(" Restaurants in ");
		description.append(location + ". ");

		description.append("Restaurants serving ");
		description.append(tagName);
		description.append(" in ");
		description.append(location + ". ");

		description.append("Foodviews, Ratings and Recommendations for ");
		description.append(tagName);
		description.append(" in ");
		description.append(location + " - ");
		description.append(tagName + " Restaurants");

		return description.toString();
	}
	
	private String getSearchDescription(String searchString, String location) {
		return "Bananaa Search for '" + searchString + "' in " + location;
	}
	
	private class TagSearch {
		private ModelAndView modelAndView;
		private Integer page;
		private String city;
		private String locality;
		private boolean isCitySearch;
		
		public TagSearch(ModelAndView modelAndView, Integer page, boolean isCitySearch, String city, String locality) {
			this.modelAndView = modelAndView;
			this.page = page;
			this.isCitySearch = isCitySearch;
			this.city = city;
			this.locality = locality;
		}

		public ModelAndView getModelAndView() {
			return modelAndView;
		}

		public Integer getPage() {
			if (this.page == null || this.page == 0) {
				this.page = 1;
			}
			return page;
		}

		public String getCity() {
			return city;
		}

		public String getLocality() {
			return locality;
		}

		public boolean isCitySearch() {
			return isCitySearch;
		}
	}
}
