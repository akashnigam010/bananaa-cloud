package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.dish.dto.ItemImageDto;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.ItemImageEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class ManagementDaoMapper {

	public DishEntity map(AddItemRequest request, MerchantEntity merchant) {
		DishEntity entity = new DishEntity();
		entity.setName(request.getName());
		entity.setNameId(request.getNameId());
		entity.setMerchant(merchant);
		entity.setCuisineId(request.getCuisineId());
		entity.setSuggestionId(request.getSuggestionId());
		entity.setImageUrl(request.getImageUrl());
		entity.setIsActive(request.getIsActive());
		entity.setInitialDump(request.getRecommendations());
		return entity;
	}

	public List<CuisineDto> mapCuisine(List<CuisineEntity> entities) {
		List<CuisineDto> dtos = new ArrayList<>();
		for (CuisineEntity entity : entities) {
			dtos.add(map(entity));
		}
		return dtos;
	}

	public List<SuggestionDto> mapSuggestion(List<SuggestionEntity> entities) {
		List<SuggestionDto> dtos = new ArrayList<>();
		for (SuggestionEntity entity : entities) {
			dtos.add(map(entity));
		}
		return dtos;
	}

	public CuisineDto map(CuisineEntity entity) {
		CuisineDto dto = new CuisineDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	public SuggestionDto map(SuggestionEntity entity) {
		SuggestionDto dto = new SuggestionDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	public List<ItemImageDto> mapItemImages(List<ItemImageEntity> entities) {
		List<ItemImageDto> dtos = new ArrayList<>();
		ItemImageDto dto = null;
		for (ItemImageEntity entity : entities) {
			dto = new ItemImageDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setUrl(entity.getUrl());
			dtos.add(dto);
		}
		return dtos;
	}
}
