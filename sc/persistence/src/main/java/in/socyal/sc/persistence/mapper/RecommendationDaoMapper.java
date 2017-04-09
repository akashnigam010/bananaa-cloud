package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.TrendingMerchantResultEntity;

@Component
public class RecommendationDaoMapper {
	@Autowired
	MerchantDaoMapper merchantMapper;
	@Autowired
	DishDaoMapper dishMapper;
	@Autowired
	UserDaoMapper userMapper;

	public void map(List<TrendingMerchantResultEntity> result, List<TrendingMerchantResultDto> response) {
		for (TrendingMerchantResultEntity entity : result) {
			TrendingMerchantResultDto dto = new TrendingMerchantResultDto();
			map(entity, dto);
			response.add(dto);
		}
	}

	public void map(TrendingMerchantResultEntity entity, TrendingMerchantResultDto dto) {
		MerchantDto merchant = new MerchantDto();
		MerchantFilterCriteria criteria = new MerchantFilterCriteria(true, true, false, false, false, true);
		merchantMapper.map(entity.getMerchant(), merchant, criteria);
		dto.setMerchant(merchant);
		dto.setRecommendations(entity.getRecommendations());
	}

	public void map(RecommendationEntity entity, RecommendationDto dto) {
		MerchantFilterCriteria criteria = new MerchantFilterCriteria(true, true, false, false, false, true);
		DishDto dish = dishMapper.map(entity.getDish(), criteria);
		dto.setDish(dish);
		dto.setId(entity.getId());
		dto.setIsActive(entity.getIsActive());
		dto.setCreatedDateTime(entity.getCreatedDateTime());
		dto.setUpdatedDateTime(entity.getUpdatedDateTime());
		UserDto user = new UserDto();
		userMapper.map(entity.getUser(), user);
		dto.setUser(user);
	}
}
