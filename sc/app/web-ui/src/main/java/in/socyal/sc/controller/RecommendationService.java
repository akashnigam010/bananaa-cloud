package in.socyal.sc.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.helper.ResponseHelper;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.Item;
import in.socyal.sc.api.item.response.ItemsResponse;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.response.EditRecommendationResponse;
import in.socyal.sc.app.rcmdn.RecommendationDelegate;
import in.socyal.sc.core.validation.RecommendationValidator;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;

@RestController
@RequestMapping(value = "/socyal/recommendation")
public class RecommendationService {
	private static final Logger LOG = Logger.getLogger(RecommendationService.class);

	@Autowired
	ResponseHelper helper;
	@Autowired
	RecommendationValidator validator;
	@Autowired
	RecommendationDelegate delegate;
	/*
	 * FIXME: remove jwtTokenHelper wiring once actual implementation is done
	 * and temp logic is removed
	 */
	@Autowired
	JwtTokenHelper jwtTokenHelper;

	@RequestMapping(value = "/addRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public EditRecommendationResponse addRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		EditRecommendationResponse response = new EditRecommendationResponse();
		try {
			validator.validateAddRecommendationRequest(request, blc);
			// FIXME: delegate.addRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/updateRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public EditRecommendationResponse updateRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		EditRecommendationResponse response = new EditRecommendationResponse();
		try {
			validator.validateUpdateRecommendationRequest(request, blc);
			// FIXME: delegate.addRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/removeRecommendation", method = RequestMethod.POST, headers = "Accept=application/json")
	public EditRecommendationResponse removeRecommendation(@RequestBody EditRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		EditRecommendationResponse response = new EditRecommendationResponse();
		try {
			validator.validateRemoveRecommendationRequest(request, blc);
			// FIXME: delegate.addRecommendation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getPopularItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemsResponse getPopularItems(@RequestBody GetRecommendationRequest request) {
		ItemsResponse response = new ItemsResponse();
		try {
			validator.validateGetRecommendationsRequest(request);
			/*
			 * FIXME: response = delegate.getPopularDishes(request); and remove
			 * below temp logic
			 */
			response = getPopularDishes();
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getMyRecommendations", method = RequestMethod.POST, headers = "Accept=application/json")
	public RecommendationResponse getMyRecommendations(@RequestBody GetRecommendationRequest request,
			@CookieValue(name = "blc", defaultValue = "") String blc) {
		RecommendationResponse response = new RecommendationResponse();
		try {
			validator.validateGetMyRecommendationsRequest(request, blc);
			response = delegate.getMyRecommendations(request);
			return helper.success(response);
		} catch (BusinessException e) {
			LOG.debug(e.getMessage());
			return helper.failure(response, e);
		}
	}

	private ItemsResponse getPopularDishes() throws BusinessException {
		ItemsResponse response = new ItemsResponse();
		Item dish = new Item();
		dish.setId(1);
		dish.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish.setName("Joojeh Kebab");
		dish.setNameId("joojeh-kebeb");
		dish.setRecommendations(22);
		response.getItems().add(dish);
		Item dish2 = new Item();
		dish2.setId(2);
		dish2.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish2.setName("Lowley Sirley");
		dish2.setNameId("lowley-shirley");
		dish2.setRecommendations(15);
		response.getItems().add(dish2);
		Item dish3 = new Item();
		dish3.setId(3);
		dish3.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish3.setName("Arrabiata Pasta");
		dish3.setNameId("arrabiata-pasta");
		dish3.setRecommendations(12);
		response.getItems().add(dish3);
		Item dish4 = new Item();
		dish4.setId(4);
		dish4.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish4.setName("Mango Delight Punch");
		dish4.setNameId("mango-delight");
		dish4.setRecommendations(10);
		response.getItems().add(dish4);

		Item dish5 = new Item();
		dish5.setId(2);
		dish5.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish5.setName("Lowley Sirley");
		dish5.setNameId("lowley-shirley");
		dish5.setRecommendations(15);
		response.getItems().add(dish5);
		Item dish6 = new Item();
		dish6.setId(3);
		dish6.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish6.setName("Arrabiata Pasta");
		dish6.setNameId("arrabiata-pasta");
		dish6.setRecommendations(12);
		response.getItems().add(dish6);
		Item dish7 = new Item();
		dish7.setId(4);
		dish7.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish7.setName("Mango Delight Punch");
		dish7.setNameId("mango-delight");
		dish7.setRecommendations(10);
		response.getItems().add(dish7);

		Item dish8 = new Item();
		dish8.setId(2);
		dish8.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish8.setName("Lowley Sirley");
		dish8.setNameId("lowley-shirley");
		dish8.setRecommendations(15);
		response.getItems().add(dish8);
		Item dish9 = new Item();
		dish9.setId(3);
		dish9.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/joojeh-kebab.jpg");
		dish9.setName("Arrabiata Pasta");
		dish9.setNameId("arrabiata-pasta");
		dish9.setRecommendations(12);
		response.getItems().add(dish9);
		Item dish10 = new Item();
		dish10.setId(4);
		dish10.setImageUrl("https://s3.ap-south-1.amazonaws.com/bananaimages/lowley-shirley.jpeg");
		dish10.setName("Mango Delight Punch");
		dish10.setNameId("mango-delight");
		dish10.setRecommendations(10);
		response.getItems().add(dish10);
		return response;
	}
}
