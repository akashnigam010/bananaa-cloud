package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.dish.dto.DishFilterCriteria;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
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
		MerchantFilterCriteria criteria = new MerchantFilterCriteria(true, true, false, false, false, false);
		merchantMapper.map(entity.getMerchant(), merchant, criteria);
		dto.setMerchant(merchant);
		dto.setRating(entity.getRating().floatValue());
	}

	public void map(RecommendationEntity entity, RecommendationDto dto) {
		DishFilterCriteria dishFilter = new DishFilterCriteria(false, false, false, true);
		MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(false);
		DishDto dish = dishMapper.map(entity.getDish(), merchantCriteria, dishFilter);
		dto.setDish(dish);
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		dto.setRating(entity.getRating());
		dto.setUpdatedDateTime(entity.getUpdatedDateTime());
	}
}
