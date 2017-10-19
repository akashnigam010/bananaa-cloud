package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.ItemRecommendationResponse;
import in.socyal.sc.api.merchant.response.MyFoodviewsResponse;
import in.socyal.sc.api.merchant.response.UserFoodviewsResponse;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.ReviewRequest;

public interface RecommendationDelegate {
	public MyFoodviewsResponse getMyRecommendations(GetRecommendationRequest request) throws BusinessException;

	public ItemRecommendationResponse getMyDishRecommendation(Integer itemId) throws BusinessException;
	
	public UserFoodviewsResponse getUsersFoodviews(GetRecommendationRequest request) throws BusinessException;

	public void saveRating(RatingRequest request) throws BusinessException;
	
	public void saveReview(ReviewRequest request) throws BusinessException;
	
	public void addRecommendation(EditRecommendationRequest request) throws BusinessException;

	public void removeRecommendation(EditRecommendationRequest request) throws BusinessException;

	public void updateRecommendation(EditRecommendationRequest request) throws BusinessException;
}
