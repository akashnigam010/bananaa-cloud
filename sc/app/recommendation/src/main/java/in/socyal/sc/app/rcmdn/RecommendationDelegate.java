package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.IdPageRequest;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.FoodviewsResponse;
import in.socyal.sc.api.merchant.response.UserFoodviewsResponse;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.ReviewRequest;

public interface RecommendationDelegate {
	public FoodviewsResponse getMyRecommendations(GetRecommendationRequest request) throws BusinessException;
	
	public FoodviewsResponse getAllRecommendations(IdPageRequest request) throws BusinessException;

	public ItemRecommendationResponse getMyDishRecommendations(Integer itemId) throws BusinessException;
	
	public UserFoodviewsResponse getUsersFoodviewsForItem(GetRecommendationRequest request) throws BusinessException;

	public void saveRating(RatingRequest request) throws BusinessException;
	
	public void saveReview(ReviewRequest request) throws BusinessException;
	
	public void addRecommendation(EditRecommendationRequest request) throws BusinessException;

	public void removeRecommendation(EditRecommendationRequest request) throws BusinessException;

	public void updateRecommendation(EditRecommendationRequest request) throws BusinessException;
}
