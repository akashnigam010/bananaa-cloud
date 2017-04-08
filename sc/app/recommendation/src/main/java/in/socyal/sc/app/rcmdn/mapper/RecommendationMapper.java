package in.socyal.sc.app.rcmdn.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.response.Recommendation;
import in.socyal.sc.api.merchant.response.RecommendationResponse;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;

@Component
public class RecommendationMapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public void map(List<RecommendationDto> result, RecommendationResponse response) {
		List<Recommendation> recommendations = new ArrayList<>();
		for (RecommendationDto dto : result) {
			Recommendation recommendation = new Recommendation();
			//FIX this: fetch review description
			recommendation.setDescription("Sample description set in mapper:fix this");
			recommendation.setId(dto.getId());
			recommendation.setItemId(dto.getDish().getId());
			recommendation.setName(dto.getDish().getName());
			recommendations.add(recommendation);
		}
		
		response.setRecommendations(recommendations);
	}

}
