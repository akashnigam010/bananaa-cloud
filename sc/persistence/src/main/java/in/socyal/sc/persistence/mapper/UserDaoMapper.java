package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.TimestampHelper;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class UserDaoMapper {
	@Autowired
	DishDaoMapper dishMapper;
	@Autowired
	TimestampHelper timestampHelper;

	public UserEntity map(UserDto from) {
		Calendar cal = Calendar.getInstance();
		UserEntity to = new UserEntity(cal, cal);
		to.setUid(from.getUid());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setNameId(from.getNameId());
		to.setEmail(from.getEmail());
		to.setImageUrl(from.getImageUrl());
		to.setCredibility(from.getCredibility());
		return to;
	}

	public void map(UserEntity from, UserDto to, Boolean mapRecommendations) {
		to.setId(from.getId());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setImageUrl(from.getImageUrl());
		to.setUserUrl("/user/" + from.getNameId());
		to.setNameId(from.getNameId());
		to.setCredibility(from.getCredibility());
		MutablePair<Integer, Integer> ratingAndReviewCount = dishMapper.getRatingCount(from.getRecommendations());
		to.setTotalRatings(ratingAndReviewCount.getLeft());
		to.setTotalReviews(ratingAndReviewCount.getRight());
		if (mapRecommendations) {
			List<RecommendationDto> dtos = new ArrayList<>();
			RecommendationDto dto = null;
			for (RecommendationEntity entity : from.getRecommendations()) {
				dto = new RecommendationDto();
				dto.setId(entity.getId());
				dto.setRating(entity.getRating());
				dto.setUpdatedDateTime(entity.getUpdatedDateTime());
				dto.setTimeDiff(timestampHelper.getTimeDiffString(entity.getUpdatedDateTime().getTimeInMillis()));
				dto.setDescription(entity.getDescription());
				dto.setDish(dishMapper.miniMap(entity.getDish()));
				dtos.add(dto);
			}
			Collections.sort(dtos);
			to.setRecommendations(dtos);
		}
	}
}
