package in.socyal.sc.app.rcmdn.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.response.Recommendation;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.date.util.TimestampHelper;

@Component
public class RecommendationMapper implements Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	TimestampHelper timestampHelper;

	public List<Recommendation> map(List<RecommendationDto> dtos) {
		List<Recommendation> recommendations = new ArrayList<>();
		Recommendation recommendation = null;
		for (RecommendationDto dto : dtos) {
			recommendation = map(dto, null);
			recommendations.add(recommendation);
		}

		return recommendations;
	}

	public Recommendation map(RecommendationDto dto, Integer dishRcmdnCount) {
		Recommendation recommendation = new Recommendation();
		recommendation.setId(dto.getId());
		recommendation.setItemId(dto.getDish().getId());
		recommendation.setDescription(StringUtils.isBlank(dto.getDescription()) ? "" : dto.getDescription());
		recommendation.setName(dto.getDish().getName());
		recommendation.setTotalRcmdns(dishRcmdnCount);
		recommendation.setThumbnail(dto.getDish().getThumbnail());
		recommendation.setTimeDiff(timestampHelper.getTimeDiffString(dto.getUpdatedDateTime().getTimeInMillis()));
		return recommendation;
	}
}
