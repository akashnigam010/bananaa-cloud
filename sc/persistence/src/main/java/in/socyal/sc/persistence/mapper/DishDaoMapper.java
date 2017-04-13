package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.items.dto.DishDetailsResultDto;
import in.socyal.sc.api.items.dto.PopularDishesResultDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.persistence.entity.DishDetailsResult;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.PopularDishesResult;

@Component
public class DishDaoMapper {
	@Autowired
	MerchantDaoMapper merchantMapper;

	public DishDto map(DishEntity entity, MerchantFilterCriteria merchantCriteria) {
		DishDto dto = new DishDto();
		dto.setId(entity.getId());
		dto.setCuisineId(entity.getCuisineId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setInitialDump(entity.getInitialDump());
		dto.setIsActive(entity.getIsActive());
		if (merchantCriteria != null) {
			MerchantDto merchant = new MerchantDto();
			merchantMapper.map(entity.getMerchant(), merchant, merchantCriteria);
			dto.setMerchant(merchant);
		}
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setSuggestionId(entity.getSuggestionId());
		return dto;
	}

	public List<DishDto> map(List<DishEntity> entities, MerchantFilterCriteria merchantCriteria) {
		List<DishDto> dtos = new ArrayList<>();
		for (DishEntity entity : entities) {
			dtos.add(map(entity, merchantCriteria));
		}
		return dtos;
	}
	
	public List<PopularDishesResultDto> map(List<PopularDishesResult> result) {
		List<PopularDishesResultDto> response = new ArrayList<>();
		MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		for (PopularDishesResult dish : result) {
			PopularDishesResultDto dto = new PopularDishesResultDto();
			dto.setDish(map(dish.getDish(), merchantCriteria));
			dto.setRecommendations(dish.getRecommendations());
			response.add(dto);
		}
		return response;
	}
	
	public DishDetailsResultDto map(DishDetailsResult result) {
		DishDetailsResultDto dto = new DishDetailsResultDto();
		MerchantFilterCriteria criteria = new MerchantFilterCriteria(true);
		dto.setDish(map(result.getDish(), criteria));
		dto.setRecommendations(result.getRecommendations());
		return dto;
	}
}
