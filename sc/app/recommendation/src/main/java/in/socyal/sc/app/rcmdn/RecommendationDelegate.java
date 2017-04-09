package in.socyal.sc.app.rcmdn;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.recommendation.request.EditRecommendationRequest;
import in.socyal.sc.api.recommendation.request.GetRecommendationRequest;

public interface RecommendationDelegate {
	public RecommendationResponse getMyRecommendations(GetRecommendationRequest request) throws BusinessException;
	public void addRecommendation(EditRecommendationRequest request) throws BusinessException;
	public void removeRecommendation(EditRecommendationRequest request) throws BusinessException;
	public void updateRecommendation(EditRecommendationRequest request) throws BusinessException;
}
