package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class TagDaoMapper {

	public void map(SuggestionEntity entity, SuggestionDto dto) {
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setThumbnail(entity.getThumbnail());
		dto.setImageUrl(entity.getImageUrl());
	}

	public void map(CuisineEntity entity, CuisineDto dto) {
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setThumbnail(entity.getThumbnail());
		dto.setImageUrl(entity.getImageUrl());
	}
}
