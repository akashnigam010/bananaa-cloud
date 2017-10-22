package in.socyal.sc.app.rcmdn.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.response.Foodview;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.date.util.TimestampHelper;

@Component
public class RecommendationMapper implements Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	TimestampHelper timestampHelper;

	public List<Foodview> map(List<RecommendationDto> dtos) {
		List<Foodview> recommendations = new ArrayList<>();
		Foodview recommendation = null;
		for (RecommendationDto dto : dtos) {
			recommendation = map(dto);
			recommendations.add(recommendation);
		}

		return recommendations;
	}

	public Foodview map(RecommendationDto dto) {
		Foodview recommendation = new Foodview();
		recommendation.setId(dto.getId());
		recommendation.setItemId(dto.getDish().getId());
		recommendation.setMerchantId(dto.getDish().getMerchant().getId());
		recommendation.setMerchantName(dto.getDish().getMerchant().getName());
		recommendation.setDescription(StringUtils.isBlank(dto.getDescription()) ? "" : dto.getDescription());
		recommendation.setName(dto.getDish().getName());
		recommendation.setTotalRcmdns(dto.getDish().getRecommendationCount());
		recommendation.setThumbnail(dto.getDish().getThumbnail());
		recommendation.setTimeDiff(timestampHelper.getTimeDiffString(dto.getUpdatedDateTime().getTimeInMillis()));
		recommendation.setRating(dto.getRating() != null ? dto.getRating().toString() : "");
		return recommendation;
	}
}
