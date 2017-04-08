package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.persistence.entity.DishEntity;

@Component
public class DishDaoMapper {
	@Autowired MerchantDaoMapper merchantMapper;
	
	public void map(DishEntity entity, DishDto dto) {
		dto.setId(entity.getId());
		dto.setCuisineId(entity.getCuisineId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setInitialDump(entity.getInitialDump());
		dto.setIsActive(entity.getIsActive());
		MerchantDto merchant = new MerchantDto();
		MerchantFilterCriteria criteria = new MerchantFilterCriteria(true, true, false, false, false, true);
		merchantMapper.map(entity.getMerchant(), merchant, criteria);
		dto.setMerchant(merchant);
		dto.setName(entity.getName());
		dto.setSuggestionId(entity.getSuggestionId());
	}
}
