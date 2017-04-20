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
import org.springframework.web.servlet.ModelAndView;

import in.socyal.sc.api.DetailsRequest;
import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.items.request.GetPopularItemsRequest;
import in.socyal.sc.api.login.response.LoginStatus;
import in.socyal.sc.api.merchant.response.ItemDetailsResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.UserDetailsResponse;
import in.socyal.sc.api.type.CityType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.user.UserDelegate;

@Controller
public class HomeController {
	private static final Logger LOG = Logger.getLogger(HomeController.class);
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String HOME_URL = "home.url";
	private static final String HOME_TITLE = "home.title";
	private static final String HOME_DESCRIPTION = "home.description";
	private static final String DETAIL_DESCRIPTION_1 = "detail.description.1";
	private static final String DETAIL_DESCRIPTION_2 = "detail.description.2";
	private static final String DETAIL_TITLE_END = "detail.title.end";
	private static final String ITEM_DETAIL_DESCRIPTION_1 = "item.detail.description.1";
	private static final String ITEM_DETAIL_DESCRIPTION_2 = "item.detail.description.2";
	private static final String ITEM_DETAIL_TITLE_END = "item.detail.title.end";

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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@CookieValue(name = "city", defaultValue = "") String city) {
		if (StringUtils.isEmpty(city)) {
			Cookie cityCookie = new Cookie("city", CityType.HYDERABAD.getName());
			cityCookie.setPath("/");
			httpResponse.addCookie(cityCookie);
		}
		return "redirect:hyderabad";
	}

	@RequestMapping(value = "/bna/manage/managementConsole", method = RequestMethod.GET)
	public ModelAndView managementConsole() {
		ModelAndView modelAndView = new ModelAndView("manage");
		return modelAndView;
	}

	private LoginStatus loginHandler(String bnaLoginCookie) {
		jwtHelper.setAuthUser(bnaLoginCookie);
		LoginStatus loginStatus = new LoginStatus();
		if (jwtHelper.isUserLoggedIn()) {
			loginStatus.setStatus(true);
			try {
				loginStatus.setFirstName(jwtHelper.getFirstName());
			} catch (BusinessException e) {
				LOG.debug("Firstname not found even after checking the login status");
				loginStatus.setStatus(false);
			}
		}
		return loginStatus;
	}

	@RequestMapping(value = "/{city}", method = RequestMethod.GET)
	public ModelAndView cityHome(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@PathVariable("city") String city) throws BusinessException {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		CityType cityType = CityType.getCity(city);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("loginStatus", loginStatus);
		modelAndView.addObject("city", cityType.getName());
		modelAndView.addObject("description", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("fbDescription", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("title", resource.getString(HOME_TITLE));
		modelAndView.addObject("url", resource.getString(HOME_URL) + "/" + cityType.getName());
		return modelAndView;
	}

	@RequestMapping(value = "/{city}/{nameId}", method = RequestMethod.GET)
	public ModelAndView merchantDetails(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@PathVariable("city") String city, @PathVariable("nameId") String nameId) throws BusinessException {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		CityType cityType = CityType.getCity(city);
		DetailsRequest request = new DetailsRequest();
		request.setMerchantNameId(nameId);
		MerchantDetailsResponse response = merchantDelegate.getMerchantDetails(request);
		GetPopularItemsRequest itemsRequest = new GetPopularItemsRequest(response.getId(), 5, 1);
		ItemsResponse itemsResponse = itemDelegate.getPopularItems(itemsRequest);
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("loginStatus", loginStatus);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("popularDishes", itemsResponse);
		modelAndView.addObject("description", getMerchantMetaDescription(response));
		modelAndView.addObject("fbDescription", getMerchantMetaDescription(response));
		modelAndView.addObject("title", getMerchantMetaTitle(response));
		modelAndView.addObject("url", getMerchantMetaUrl(response, cityType.getName()));
		return modelAndView;
	}

	@RequestMapping(value = "/{city}/{merchantNameId}/{itemNameId}", method = RequestMethod.GET)
	public ModelAndView foodDetails(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@PathVariable("city") String city, @PathVariable("merchantNameId") String merchantNameId,
			@PathVariable("itemNameId") String itemNameId) throws BusinessException {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		ModelAndView modelAndView = new ModelAndView("item-detail");
		DetailsRequest detailsRequest = new DetailsRequest();
		detailsRequest.setItemNameId(itemNameId);
		detailsRequest.setMerchantNameId(merchantNameId);
		ItemDetailsResponse response = itemDelegate.getItemDetails(detailsRequest);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("loginStatus", loginStatus);
		modelAndView.addObject("description", getItemMetaDescription(response));
		modelAndView.addObject("fbDescription", getItemMetaDescription(response));
		modelAndView.addObject("title", getItemMetaTitle(response));
		modelAndView.addObject("url", getItemMetaUrl(response));
		return modelAndView;
	}
	
	@RequestMapping(value = "/user/{userNameId}", method = RequestMethod.GET)
	public ModelAndView userDetails(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@PathVariable("userNameId") String userNameId) throws BusinessException {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		ModelAndView modelAndView = new ModelAndView("user-detail");
		UserDetailsResponse response = userDelegate.getUserDetails(userNameId);
		modelAndView.addObject("detail", response);
		modelAndView.addObject("loginStatus", loginStatus);
		modelAndView.addObject("description", "");
		modelAndView.addObject("fbDescription", "");
		modelAndView.addObject("title", "");
		modelAndView.addObject("url", "");
		return modelAndView;
	}

	private String getMerchantMetaDescription(MerchantDetailsResponse response) {
		String description = response.getName() + "; ";
		description += response.getName() + ", " + response.getShortAddress() + "; ";
		description += resource.getString(DETAIL_DESCRIPTION_1);
		description += " " + response.getName() + " ";
		description += resource.getString(DETAIL_DESCRIPTION_2);
		return description;
	}

	private String getMerchantMetaTitle(MerchantDetailsResponse response) {
		String title = response.getName() + ", " + response.getShortAddress() + " ";
		title += resource.getString(DETAIL_TITLE_END);
		return title;
	}

	private String getMerchantMetaUrl(MerchantDetailsResponse response, String city) {
		String url = resource.getString(HOME_URL);
		url += "/" + city + "/" + response.getNameId();
		return url;
	}

	private String getItemMetaDescription(ItemDetailsResponse response) {
		String description = response.getDish().getName() + "; ";
		description += response.getDish().getName() + " @ " + response.getDish().getMerchant().getName() + "; ";
		description += resource.getString(ITEM_DETAIL_DESCRIPTION_1);
		description += " " + response.getDish().getName() + " ";
		description += resource.getString(ITEM_DETAIL_DESCRIPTION_2);
		return description;
	}

	private String getItemMetaTitle(ItemDetailsResponse response) {
		String title = response.getDish().getName() + " @ " + response.getDish().getMerchant().getName() + " ";
		title += resource.getString(ITEM_DETAIL_TITLE_END);
		return title;
	}

	private String getItemMetaUrl(ItemDetailsResponse response) {
		return resource.getString(HOME_URL) + "/" + response.getDish().getItemUrl();
	}
}
