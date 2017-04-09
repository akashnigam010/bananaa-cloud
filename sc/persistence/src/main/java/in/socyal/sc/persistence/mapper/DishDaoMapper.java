package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.persistence.entity.DishEntity;

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
}
