package in.socyal.sc.app.rcmdn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.IdPageRequest;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.Foodview;
import in.socyal.sc.api.merchant.response.FoodviewsResponse;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.UserFoodview;
import in.socyal.sc.api.merchant.response.UserFoodviewsResponse;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.FoodviewRequest;
import in.socyal.sc.api.type.error.DishErrorCodeType;
import in.socyal.sc.api.type.error.UserErrorCodeType;
import in.socyal.sc.app.rcmdn.mapper.RecommendationMapper;
import in.socyal.sc.helper.security.jwt.JwtTokenHelper;
import in.socyal.sc.persistence.DishDao;
import in.socyal.sc.persistence.RecommendationDao;

@Component
public class RecommendationDelegateImpl implements RecommendationDelegate {
	@Autowired
	RecommendationDao dao;
	@Autowired
	DishDao dishDao;
	@Autowired
	JwtTokenHelper jwtHelper;
	@Autowired
	RecommendationMapper mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void saveRating(RatingRequest request) throws BusinessException {
		if (!dishDao.isDishExists(request.getId())) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		dao.saveRating(request, jwtHelper.getUserId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void saveFoodview(FoodviewRequest request) throws BusinessException {
		if (!dishDao.isDishExists(request.getId())) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		dao.saveFoodview(request, jwtHelper.getUserId());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteFoodview(IdRequest request) throws BusinessException {
		dao.deleteFoodview(request, jwtHelper.getUserId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public FoodviewsResponse getMyRecommendations(GetRecommendationRequest request) throws BusinessException {
		FoodviewsResponse response = new FoodviewsResponse();
		if (!jwtHelper.isUserLoggedIn()) {
			return response;
		}
		List<RecommendationDto> result = dao.getRecommendations(jwtHelper.getUserId(), request.getMerchantId(),
				request.getPage());
		List<Foodview> rcmdns = mapper.map(result);
		response.setRecommendations(rcmdns);
		if (result.size() > 0) {
			response.setMerchantName(result.get(0).getDish().getMerchant().getName());
		}
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public FoodviewsResponse getAllRecommendations(IdPageRequest request) throws BusinessException {
		FoodviewsResponse response = new FoodviewsResponse();
		List<Foodview> rcmdns = mapper.map(dao.getRecommendations(request.getId(), null,
				request.getPage()));
		response.setRecommendations(rcmdns);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemRecommendationResponse getMyDishRecommendations(Integer itemId) throws BusinessException {
		ItemRecommendationResponse response = new ItemRecommendationResponse();
		if (!jwtHelper.isUserLoggedIn()) {
			return response;
		}
		RecommendationDto dto = dao.getMyDishRecommendation(jwtHelper.getUserId(), itemId);
		if (dto != null) {
			response.setRecommended(Boolean.TRUE);
			response.setRecommendation(mapper.map(dto));
		}
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public UserFoodviewsResponse getUsersFoodviewsForItem(GetRecommendationRequest request) throws BusinessException {
		UserFoodviewsResponse response = new UserFoodviewsResponse();
		// whether user is logged in or not is already checked in validator.
		// It is mandatory that the user must be logged in post this point
		List<UserFoodview> dtos = dao.getOtherUsersFoodviews(jwtHelper.getUserId(), request.getItemId(),
				request.getPage(), 10);
		response.setFoodviews(dtos);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void addRecommendation(EditRecommendationRequest request) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		if (!dishDao.isDishExists(request.getDishId())) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		dao.addRecommendation(jwtHelper.getUserId(), request.getDishId(), request.getDescription());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void removeRecommendation(EditRecommendationRequest request) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		dao.removeRecommendation(request.getRcmdnId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateRecommendation(EditRecommendationRequest request) throws BusinessException {
		if (!jwtHelper.isUserLoggedIn()) {
			throw new BusinessException(UserErrorCodeType.USER_NOT_LOGGED_IN);
		}
		dao.updateRecommendation(request.getRcmdnId(), request.getDescription());
	}
}
