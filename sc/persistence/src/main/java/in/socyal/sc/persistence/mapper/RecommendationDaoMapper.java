package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.dish.dto.DishFilterCriteria;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.response.UserFoodview;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.TimestampHelper;
import in.socyal.sc.persistence.entity.RecommendationEntity;

@Component
public class RecommendationDaoMapper {
	@Autowired
	MerchantDaoMapper merchantMapper;
	@Autowired
	DishDaoMapper dishMapper;
	@Autowired
	UserDaoMapper userMapper;
	@Autowired
	TimestampHelper timestampHelper;

	public void map(RecommendationEntity entity, RecommendationDto dto) {
		DishFilterCriteria dishFilter = new DishFilterCriteria(true, true);
		MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(false);
		DishDto dish = dishMapper.map(entity.getDish(), merchantCriteria, dishFilter);
		dto.setDish(dish);
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		dto.setRating(entity.getRating());
		dto.setUpdatedDateTime(entity.getUpdatedDateTime());
	}
	
	public List<UserFoodview> mapRecommendationsWithUser(List<RecommendationEntity> entities) {
		List<UserFoodview> dtos = new ArrayList<>();
		for (RecommendationEntity entity : entities) {
			dtos.add(mapWithUser(entity));
		}
		return dtos;
	}
	
	private UserFoodview mapWithUser(RecommendationEntity entity) {
		UserFoodview dto = new UserFoodview();
		UserDto userDto = new UserDto();
		userMapper.map(entity.getUser(), userDto, false, false);
		dto.setId(entity.getId());
		dto.setUserId(userDto.getId());
		dto.setUserName(userDto.getName());
		dto.setUserImageUrl(userDto.getImageUrl());
		dto.setUserRatingCount(userDto.getTotalRatings());
		dto.setUserFoodviewCount(userDto.getTotalReviews());
		dto.setRating(entity.getRating() != null ? entity.getRating().toString() : "");
		dto.setDesc(entity.getDescription());
		dto.setTimeDiff(timestampHelper.getTimeDiffString(entity.getUpdatedDateTime().getTimeInMillis()));
		return dto;
	}
}
