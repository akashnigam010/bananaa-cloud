package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.items.dto.DishResultDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.DishResult;
import in.socyal.sc.persistence.entity.RecommendationEntity;

@Component
public class DishDaoMapper {
	@Autowired
	MerchantDaoMapper merchantMapper;
	@Autowired
	UserDaoMapper userMapper;

	public DishDto map(DishEntity entity, MerchantFilterCriteria merchantCriteria) {
		DishDto dto = new DishDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setSuggestionId(entity.getSuggestionId());
		dto.setCuisineId(entity.getCuisineId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setInitialDump(entity.getInitialDump());
		dto.setIsActive(entity.getIsActive());
		dto.setRecommendations(mapRecommendations(entity));
		if (merchantCriteria != null) {
			MerchantDto merchant = new MerchantDto();
			merchantMapper.map(entity.getMerchant(), merchant, merchantCriteria);
			dto.setMerchant(merchant);
			dto.setItemUrl(dto.getMerchant().getNameId() + "/" + dto.getNameId());
		}
		return dto;
	}

	public DishDto miniMap(DishEntity entity) {
		DishDto dto = new DishDto();
		dto.setName(entity.getName());
		dto.setImageUrl(entity.getImageUrl());
		MerchantDto merchant = new MerchantDto();
		MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		merchantMapper.map(entity.getMerchant(), merchant, merchantCriteria);
		dto.setMerchant(merchant);
		dto.setItemUrl(dto.getMerchant().getNameId() + "/" + dto.getNameId());
		return dto;
	}

	private List<RecommendationDto> mapRecommendations(DishEntity entity) {
		List<RecommendationDto> dtos = new ArrayList<>();
		RecommendationDto dto = null;
		for (RecommendationEntity rcmdEntity : entity.getRecommendations()) {
			if (rcmdEntity.getIsActive()) {
				dto = new RecommendationDto();
				dto.setId(rcmdEntity.getId());
				dto.setDescription(rcmdEntity.getDescription());
				dto.setIsActive(rcmdEntity.getIsActive());
				UserDto userDto = new UserDto();
				userMapper.map(rcmdEntity.getUser(), userDto, false);
				//FIXME : Result set must be filtered with active recommendations already
				userDto.setTotalRecommendations(getActiveRecommendations(rcmdEntity.getUser().getRecommendations()));
				dto.setUser(userDto);
				dto.setUpdatedDateTime(rcmdEntity.getUpdatedDateTime());
				dtos.add(dto);
			}			
		}
		return dtos;
	}
	
	/*
	 * Remove this method once isActive flag is put in query
	 */
	private Integer getActiveRecommendations(List<RecommendationEntity> entities) {
		int activeRcmdCount = 0;
		for (RecommendationEntity entity : entities) {
			if (entity.getIsActive()) {
				activeRcmdCount ++;
			}
		}
		return activeRcmdCount;
	}

	public List<DishDto> map(List<DishEntity> entities, MerchantFilterCriteria merchantCriteria) {
		List<DishDto> dtos = new ArrayList<>();
		for (DishEntity entity : entities) {
			dtos.add(map(entity, merchantCriteria));
		}
		return dtos;
	}

	/**
	 * Projection results mapper
	 * 
	 * @param result
	 * @param merchantFilter
	 * @return
	 */
	public List<DishResultDto> mapDishResults(List<DishResult> result, MerchantFilterCriteria merchantFilter) {
		List<DishResultDto> response = new ArrayList<>();
		for (DishResult dish : result) {
			response.add(map(dish, merchantFilter));
		}
		return response;
	}

	/**
	 * Projection result mapper
	 * 
	 * @param result
	 * @param merchantFilter
	 * @return
	 */
	public DishResultDto map(DishResult result, MerchantFilterCriteria merchantFilter) {
		DishResultDto dto = new DishResultDto();
		dto.setDish(map(result.getDish(), merchantFilter));
		dto.setRecommendations(result.getRecommendations());
		return dto;
	}
}
