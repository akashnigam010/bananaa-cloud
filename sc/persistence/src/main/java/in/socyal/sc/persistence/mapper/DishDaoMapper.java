package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.items.dto.DishResultDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.DishResult;

@Component
public class DishDaoMapper {
	@Autowired
	MerchantDaoMapper merchantMapper;

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
		if (merchantCriteria != null) {
			MerchantDto merchant = new MerchantDto();
			merchantMapper.map(entity.getMerchant(), merchant, merchantCriteria);
			dto.setMerchant(merchant);
			dto.setItemUrl(dto.getMerchant().getNameId() + "/" + dto.getNameId());
		}		
		return dto;
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
