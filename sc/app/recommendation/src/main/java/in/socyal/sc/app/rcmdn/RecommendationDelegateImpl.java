package in.socyal.sc.app.rcmdn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.Recommendation;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.ReviewRequest;
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
	public void saveReview(ReviewRequest request) throws BusinessException {
		if (!dishDao.isDishExists(request.getId())) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		dao.saveReview(request, jwtHelper.getUserId());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public RecommendationResponse getMyRecommendations(GetRecommendationRequest request) throws BusinessException {
		RecommendationResponse response = new RecommendationResponse();
		if (!jwtHelper.isUserLoggedIn()) {
			return response;
		}
		List<RecommendationDto> result = dao.getMyRecommendations(
				jwtHelper.getUserId(), request.getMerchantId(), request.getPage());
		List<Recommendation> rcmdns = new ArrayList<>();
		//FIXME : Remove multiple calls for each recommendation to db to fetch total recommendation count
		for (RecommendationDto dto : result) {
			Integer dishRcmdnCount = dao.getDishRecommendationCount(dto.getDish().getId());
			rcmdns.add(mapper.map(dto, dishRcmdnCount));
		}
		response.setRecommendations(rcmdns);
		if (result.size() > 0) {
			response.setMerchantName(result.get(0).getDish().getMerchant().getName());
		}		
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public ItemRecommendationResponse getMyDishRecommendation(GetRecommendationRequest request) throws BusinessException {
		ItemRecommendationResponse response = new ItemRecommendationResponse();
		if (!jwtHelper.isUserLoggedIn()) {
			return response;
		}
		RecommendationDto dto = dao.getMyDishRecommendation(jwtHelper.getUserId(), request.getItemId());
		if (dto != null) {
			response.setRecommended(Boolean.TRUE);
			response.setRecommendation(mapper.map(dto, null));
		}
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
