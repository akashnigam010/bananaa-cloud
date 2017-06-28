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
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.items.request.TrendingRequest;
import in.socyal.sc.api.login.response.LoginStatus;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetails;
import in.socyal.sc.api.merchant.response.MerchantListForTagResponse;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.type.CityType;
import in.socyal.sc.api.type.LocalityType;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.cache.CityCache;
import in.socyal.sc.cache.CuisineCache;
import in.socyal.sc.cache.LocalityCache;
import in.socyal.sc.cache.SuggestionCache;
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@CookieValue(name = "city", defaultValue = "") String city,
			@CookieValue(name = "loc", defaultValue = "") String loc) {
		if (StringUtils.isEmpty(city)) {
			Cookie cityCookie = new Cookie("city", CityType.HYDERABAD.getName());
			cityCookie.setPath("/");
			httpResponse.addCookie(cityCookie);
		}
		return "redirect:hyderabad";
	}

	@RequestMapping(value = "/{city}", method = RequestMethod.GET)
	public ModelAndView cityHome(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String loc, @PathVariable("city") String city)
			throws BusinessException {
		ModelAndView modelAndView = new ModelAndView("index");
		setCommonModelData(modelAndView, bnaLoginCookie, city, loc);
		modelAndView.addObject("description", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("fbDescription", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("title", resource.getString(HOME_TITLE));
		String[] urlParams = { city };
		modelAndView.addObject("url", getMetaUrl(urlParams));
		modelAndView.addObject("imageUrl", resource.getString(HOME_IMAGE));
		return modelAndView;
	}

	/**
	 * Accepted URLS :<br>
	 * - bananaa.in/city/restaurant <br>
	 * - bananaa.in/city/locality
	 * 
	 * @param bnaLoginCookie
	 * @param loc
	 * @param city
	 * @param restaurantOrLocalityNameId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/{city}/{restaurantOrLocalityNameId}", method = RequestMethod.GET)
	public ModelAndView restaurantDetailsOrListInALocality(
			@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String loc,
			@PathVariable("restaurantOrLocalityNameId") String restaurantOrLocalityNameId,
			@PathVariable("city") String city) throws BusinessException {
		LocalityDto locality = localityCache.getLocality(restaurantOrLocalityNameId);
		if (locality == null) {
			// URL : bananaa.in/hyderabad/fusion-9-hitech-city
			DetailsRequest request = new DetailsRequest();
			request.setMerchantNameId(restaurantOrLocalityNameId);
			MerchantDetails response = merchantDelegate.getMerchantDetails(request);
			TrendingRequest itemsRequest = new TrendingRequest(response.getId(), 5, 1);
			ItemsResponse itemsResponse = itemDelegate.getPopularItems(itemsRequest);
			ModelAndView modelAndView = new ModelAndView("detail");
			setCommonModelData(modelAndView, bnaLoginCookie, city, loc);
			modelAndView.addObject("detail", response);
			modelAndView.addObject("popularDishes", itemsResponse);
			modelAndView.addObject("popularCuisines", response.getRatedCuisines());
			modelAndView.addObject("description", getMerchantMetaDescription(response));
			modelAndView.addObject("fbDescription", getMerchantMetaDescription(response));
			modelAndView.addObject("title", getMerchantMetaTitle(response));
			String[] urlParams = { city, restaurantOrLocalityNameId };
			modelAndView.addObject("url", getMetaUrl(urlParams));
			modelAndView.addObject("imageUrl", response.getThumbnail());
			return modelAndView;
		} else {
			// URL : bananaa.in/hyderabad/hitech-city
			return null;
		}

	}

	/**
	 * Accepted URLS :<br>
	 * - bananaa.in/city/restaurant/item <br>
	 * - bananaa.in/city/locality/tag
	 * 
	 * @param bnaLoginCookie
	 * @param loc
	 * @param city
	 * @param restaurantOrLocalityNameId
	 * @param itemOrTagNameId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/{city}/{restaurantOrLocalityNameId}/{itemOrTagNameId}", method = RequestMethod.GET)
	public ModelAndView foodDetails(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String loc, @PathVariable("city") String city,
			@PathVariable("restaurantOrLocalityNameId") String restaurantOrLocalityNameId,
			@PathVariable("itemOrTagNameId") String itemOrTagNameId,
			@RequestParam(value = "page", required = false) Integer page) throws BusinessException {
		LocalityDto locality = localityCache.getLocality(restaurantOrLocalityNameId);
		if (locality == null) {
			// bananaa.in/hyderabad/fusion-9-hitech-city/greek-pizza
			ModelAndView modelAndView = new ModelAndView("item-detail");
			DetailsRequest detailsRequest = new DetailsRequest();
			detailsRequest.setItemNameId(itemOrTagNameId);
			detailsRequest.setMerchantNameId(restaurantOrLocalityNameId);
			ItemDetailsResponse response = itemDelegate.getItemDetails(detailsRequest);
			setCommonModelData(modelAndView, bnaLoginCookie, city, loc);
			modelAndView.addObject("detail", response);
			modelAndView.addObject("description", getItemMetaDescription(response));
			modelAndView.addObject("fbDescription", getItemMetaDescription(response));
			modelAndView.addObject("title", getItemMetaTitle(response));
			String[] urlParams = { city, restaurantOrLocalityNameId, itemOrTagNameId };
			modelAndView.addObject("url", getMetaUrl(urlParams));
			modelAndView.addObject("imageUrl", response.getDish().getThumbnail());
			return modelAndView;
		} else {
			// bananaa.in/hyderabad/hitech-city/italian,
			// bananaa.in/hyderabad/hitech-city/pizza,
			// bananaa.in/hyderabad/hitech-city/cafe
			ModelAndView modelAndView = new ModelAndView("tag-search");
			SearchMerchantByTagRequest request = new SearchMerchantByTagRequest();
			request.setNameId(itemOrTagNameId);
			page = page == null ? 1 : page;
			request.setPage(page);
			request.setType(TagType.CUISINE);
			request.setLocalityNameId(locality.getNameId());
			MerchantListForTagResponse response = merchantDelegate.getMerchantsByTag(request);
			CityType cityType = setCommonModelData(modelAndView, bnaLoginCookie, city, loc);
			modelAndView.addObject("detail", response);
			String[] urlParams = { city, restaurantOrLocalityNameId, itemOrTagNameId };
			String tagName = getTagName(response, itemOrTagNameId);
			String location = getLocation(cityType, locality);
			String metaDescription = getTagMetaDescription(tagName, location);
			response.setLocation(location);
			response.setTagName(tagName);
			modelAndView.addObject("description", metaDescription);
			modelAndView.addObject("fbDescription", metaDescription);
			modelAndView.addObject("title", tagName + " in " + location);
			modelAndView.addObject("url", getMetaUrl(urlParams));
			//TODO : add image - 
			modelAndView.addObject("imageUrl", "https://bna-s3.s3.amazonaws.com/d/27/jalapeno-poppers.jpg");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/user/{userNameId}", method = RequestMethod.GET)
	public ModelAndView userDetails(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@CookieValue(name = "loc", defaultValue = "") String loc, @PathVariable("userNameId") String userNameId)
			throws BusinessException {
		ModelAndView modelAndView = new ModelAndView("user-detail");
		setCommonModelData(modelAndView, bnaLoginCookie, CityType.HYDERABAD.getName(), loc);
		UserDetailsResponse response = userDelegate.getUserDetails(userNameId);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("description", getUserMetaDescription(response));
		modelAndView.addObject("fbDescription", getUserMetaDescription(response));
		modelAndView.addObject("title", getUserMetaTitle(response));
		String[] urlParams = { "user", userNameId };
		modelAndView.addObject("url", getMetaUrl(urlParams));
		modelAndView.addObject("imageUrl", response.getUser().getImageUrl());
		return modelAndView;
	}

	private CityType setCommonModelData(ModelAndView modelAndView, String bnaLoginCookie, String city, String loc) {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		CityType cityType = cityHandler(city);
		LocalityType localityType = localityHandler(loc);
		modelAndView.addObject("loginStatus", loginStatus);
		modelAndView.addObject("location", localityType == null ? cityType.getName() : localityType.getName());
		return cityType;
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

	private CityType cityHandler(String city) {
		CityType cityType = CityType.getCity(city);
		if (cityType == null) {
			cityType = CityType.HYDERABAD;
		}
		return cityType;
	}

	private LocalityType localityHandler(String localityId) {
		LocalityType localityType = null;
		if (StringUtils.isNotBlank(localityId)) {
			localityType = LocalityType.getLocalityById(Integer.parseInt(localityId));
		}
		return localityType;
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
	
	private String getTagName(MerchantListForTagResponse response, String nameId) {
		if (response.getMerchants().size() > 0) {
			Tag tag = response.getMerchants().get(0).getSearchTag();
			if (tag != null) {
				return tag.getName(); 
			}
		}
		return nameId;
	}
	
	private String getLocation(CityType cityType, LocalityDto locality) {
		StringBuilder location = new StringBuilder();
		if (locality != null) {
			location.append(locality.getName());
			location.append(", ");
		}
		location.append(cityType.getName());
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
}
