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
import in.socyal.sc.api.merchant.response.Review;
import in.socyal.sc.api.merchant.response.User;
import in.socyal.sc.api.type.CityType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.app.rcmdn.ItemDelegate;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

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
	public ModelAndView details(@CookieValue(name = "blc", defaultValue = "") String bnaLoginCookie,
			@PathVariable("city") String city, @PathVariable("nameId") String nameId) {
		LoginStatus loginStatus = loginHandler(bnaLoginCookie);
		CityType cityType = CityType.getCity(city);
		DetailsRequest request = new DetailsRequest();
		request.setNameId(nameId);
		MerchantDetailsResponse response;
		GetPopularItemsRequest itemsRequest;
		ItemsResponse itemsResponse;
		try {
			response = merchantDelegate.getMerchantDetails(request);
			itemsRequest = new GetPopularItemsRequest(response.getId(), 5, 1);
			itemsResponse = itemDelegate.getPopularItems(itemsRequest);
		} catch (BusinessException e) {
			return new ModelAndView("404");
		}
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
		detailsRequest.setNameId(itemNameId);
		ItemDetailsResponse response = null;
		try {
			response = itemDelegate.getItemDetails(detailsRequest);
		} catch (BusinessException e) {
			return new ModelAndView("404");
		}
		modelAndView.addObject("detail", response);
		modelAndView.addObject("loginStatus", loginStatus);
		modelAndView.addObject("description", getItemMetaDescription(response));
		modelAndView.addObject("fbDescription", getItemMetaDescription(response));
		modelAndView.addObject("title", getItemMetaTitle(response));
		modelAndView.addObject("url", getItemMetaUrl(response));
		return modelAndView;
	}

	private ItemDetailsResponse getItemDetails() {
		ItemDetailsResponse response = new ItemDetailsResponse();
		response.setId(1);
		response.setName("Joojeh Kebab");
		response.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		response.setMerchantName("Fusion 9");
		response.setMerchantShortAddress("Hitech City, Hyderabad");
		response.setMerchantUrl("hyderabad/12346");
		response.setRecommendations(23);
		Review review1 = new Review();
		User user1 = new User();
		user1.setId(1);
		user1.setName("Shubhankar Saxena");
		user1.setRecommendations(10);
		user1.setImageUrl(
				"https://scontent.xx.fbcdn.net/v/t1.0-1/p160x160/15826261_1227586443984803_2081423736824561505_n.jpg?oh=c3604ca3d4199d5561c2eb4e2621ee3d&oe=5902B1FE");
		review1.setUser(user1);
		review1.setDescription(
				"The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow.The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow. T");
		response.getReviews().add(review1);

		Review review2 = new Review();
		User user2 = new User();
		user2.setId(2);
		user2.setName("Deepak Gupta");
		user2.setRecommendations(14);
		user2.setImageUrl(
				"https://scontent.xx.fbcdn.net/v/t1.0-1/p160x160/16195135_10202582907209226_8892726716716657102_n.jpg?oh=88a1701d79b3ad41916b6dd14fa254a5&oe=5938B99B");
		review2.setUser(user2);
		review2.setDescription(
				"The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow.The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow. T");
		response.getReviews().add(review2);

		Review review3 = new Review();
		User user3 = new User();
		user3.setId(3);
		user3.setName("Subhajoy Laskar");
		user3.setRecommendations(34);
		user3.setImageUrl(
				"https://scontent.xx.fbcdn.net/v/t1.0-1/p160x160/16729123_1425749337455860_4273798065565020914_n.jpg?oh=471bd266f6bf7f77846dd96a74d66337&oe=592F48FA");
		review3.setUser(user3);
		review3.setDescription(
				"The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow.");
		response.getReviews().add(review3);

		Review review4 = new Review();
		User user4 = new User();
		user4.setId(4);
		user4.setName("Ayush Singh");
		user4.setRecommendations(34);
		user4.setImageUrl(
				"https://scontent.xx.fbcdn.net/v/t1.0-1/c27.0.160.160/p160x160/15337536_987021314735785_2690017545352587728_n.jpg?oh=de9c0549fb958af561b3bcd33092776c&oe=592F7F57");
		review4.setUser(user4);
		review4.setDescription(
				"The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow.The smell is awesome, plus it tastes like meadow. The smell is awesome, plus it tastes like meadow. T");
		response.getReviews().add(review4);
		return response;
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
		String description = response.getName() + "; ";
		description += response.getName() + " @ " + response.getMerchantName() + "; ";
		description += resource.getString(ITEM_DETAIL_DESCRIPTION_1);
		description += " " + response.getName() + " ";
		description += resource.getString(DETAIL_DESCRIPTION_2);
		return description;
	}

	private String getItemMetaTitle(ItemDetailsResponse response) {
		String title = response.getName() + " @ " + response.getMerchantName() + " ";
		title += resource.getString(ITEM_DETAIL_TITLE_END);
		return title;
	}

	private String getItemMetaUrl(ItemDetailsResponse response) {
		return resource.getString(HOME_URL) + "/" + response.getItemUrl();
	}
}
