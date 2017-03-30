package in.socyal.sc.controller;

import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.request.MerchantDetailsRequest;
import in.socyal.sc.api.merchant.response.Dish;
import in.socyal.sc.api.merchant.response.DishResponse;
import in.socyal.sc.api.merchant.response.MerchantDetailsResponse;
import in.socyal.sc.api.merchant.response.Recommendation;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.type.CityType;
import in.socyal.sc.app.merchant.MerchantDelegate;
import in.socyal.sc.helper.security.jwt.JwtHelper;

@Controller
public class PageController {
	private static final Logger LOG = Logger.getLogger(PageController.class);
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String HOME_URL = "home.url";
	private static final String HOME_TITLE = "home.title";
	private static final String HOME_DESCRIPTION = "home.description";
	private static final String DETAIL_DESCRIPTION_1 = "detail.description.1";
	private static final String DETAIL_DESCRIPTION_2 = "detail.description.2";
	private static final String DETAIL_TITLE_END = "detail.title.end";
	
	@Autowired MerchantDelegate merchantDelegate;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:hyderabad";
	}
	
	@RequestMapping(value = "/hyderabad", method = RequestMethod.GET)
	public ModelAndView city() throws BusinessException {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("accessToken", JwtHelper.createJsonWebTokenForGuest());
		modelAndView.addObject("city", "hyderabad");
		modelAndView.addObject("description", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("fbDescription", resource.getString(HOME_DESCRIPTION));
		modelAndView.addObject("title", resource.getString(HOME_TITLE));
		modelAndView.addObject("url", resource.getString(HOME_URL) + "/" + CityType.HYDERABAD.getName());
		return modelAndView;
	}
	
	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public String app() {
		return "redirect:https://play.google.com/store/apps/details?id=com.bananaa";
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about() {
		ModelAndView modelAndView = new ModelAndView("about");
		return modelAndView;
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact() {
		ModelAndView modelAndView = new ModelAndView("contact");
		return modelAndView;
	}
	
	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public ModelAndView privacy() {
		ModelAndView modelAndView = new ModelAndView("privacy");
		return modelAndView;
	}
	
	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView terms() {
		ModelAndView modelAndView = new ModelAndView("terms");
		return modelAndView;
	}
	
	@RequestMapping(value = "/hyderabad/{id}", method = RequestMethod.GET)
	public ModelAndView details(@PathVariable String id) throws BusinessException {
		MerchantDetailsRequest request = new MerchantDetailsRequest();
		request.setId(Integer.parseInt(id));
		MerchantDetailsResponse response = merchantDelegate.getMerchantDetails(request);
		
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("detail", response);
		modelAndView.addObject("popularDishes", getPopularDishes());
		modelAndView.addObject("description", getDetailMetaDescription(response));
		modelAndView.addObject("fbDescription", getDetailMetaDescription(response));
		modelAndView.addObject("title", getDetailMetaTitle(response));
		modelAndView.addObject("url", getDetailMetaUrl(response, CityType.HYDERABAD.getName()));
		modelAndView.addObject("userImage", "https://fb-s-a-a.akamaihd.net/h-ak-xfl1/v/t1.0-1/p160x160/15826261_1227586443984803_2081423736824561505_n.jpg?oh=c3604ca3d4199d5561c2eb4e2621ee3d&oe=5902B1FE&__gda__=1494528018_4c3d954c3fe507b4d4aa581df02de6e7");
		modelAndView.addObject("accessToken", JwtHelper.createJsonWebTokenForGuest());
		return modelAndView;
	}
	
	private DishResponse getPopularDishes() {
		DishResponse dishResponse = new DishResponse();
		Dish dish = new Dish();
		dish.setId(1);
		dish.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish.setName("Joojeh Kebab");
		dish.setNameId("joojeh-kebeb");
		dish.setRecommendations(22);
		dishResponse.getDishes().add(dish);
		Dish dish2 = new Dish();
		dish2.setId(2);
		dish2.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish2.setName("Lowley Sirley");
		dish2.setNameId("lowley-shirley");
		dish2.setRecommendations(15);
		dishResponse.getDishes().add(dish2);
		Dish dish3 = new Dish();
		dish3.setId(3);
		dish3.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish3.setName("Arrabiata Pasta");
		dish3.setNameId("arrabiata-pasta");
		dish3.setRecommendations(12);
		dishResponse.getDishes().add(dish3);
		Dish dish4 = new Dish();
		dish4.setId(4);
		dish4.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish4.setName("Mango Delight Punch");
		dish4.setNameId("mango-delight");
		dish4.setRecommendations(10);
		dishResponse.getDishes().add(dish4);
		return dishResponse;
	}
	
	private String getDetailMetaDescription(MerchantDetailsResponse response) {
		String description = response.getName() + "; ";
		description += response.getName() + ", " + response.getShortAddress() + "; ";
		description += resource.getString(DETAIL_DESCRIPTION_1);
		description += " " + response.getName() + " ";
		description += resource.getString(DETAIL_DESCRIPTION_2);
		return description;
	}
	
	private String getDetailMetaTitle(MerchantDetailsResponse response) {
		String title = response.getName() + ", " + response.getShortAddress() + " ";
		title += resource.getString(DETAIL_TITLE_END);
		return title;
	}
	
	private String getDetailMetaUrl(MerchantDetailsResponse response, String city) {
		String url = resource.getString(HOME_URL);
		url += "/" + city + "/" + response.getId();
		return url;
	}
}
