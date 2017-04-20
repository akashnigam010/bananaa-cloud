package in.socyal.sc.persistence.entity;

public class ReviewResult {
	private RecommendationEntity recommendation;
	private UserEntity user;
	private RecommendationCount recommendationCount;

	public RecommendationEntity getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(RecommendationEntity recommendation) {
		this.recommendation = recommendation;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public RecommendationCount getRecommendationCount() {
		return recommendationCount;
	}

	public void setRecommendationCount(RecommendationCount recommendationCount) {
		this.recommendationCount = recommendationCount;
	}
}
