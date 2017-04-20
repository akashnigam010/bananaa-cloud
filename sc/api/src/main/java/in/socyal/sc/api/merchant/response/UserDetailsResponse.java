package in.socyal.sc.api.merchant.response;

import java.io.Serializable;
import java.util.List;

import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.api.user.dto.UserDto;

public class UserDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private UserDto user;
	private List<RecommendationDto> recommendations;
	private Integer totalRecommendations;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<RecommendationDto> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<RecommendationDto> recommendations) {
		this.recommendations = recommendations;
	}

	public Integer getTotalRecommendations() {
		return totalRecommendations;
	}

	public void setTotalRecommendations(Integer totalRecommendations) {
		this.totalRecommendations = totalRecommendations;
	}
}
