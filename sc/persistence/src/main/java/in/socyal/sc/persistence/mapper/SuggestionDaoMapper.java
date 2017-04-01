package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class SuggestionDaoMapper {

	public void map(SuggestionEntity entity, SuggestionDto dto) {
		dto.setId(entity.getId());
		dto.setName(entity.getName());
	}
}
