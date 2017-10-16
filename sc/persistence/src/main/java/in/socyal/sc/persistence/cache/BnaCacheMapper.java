package in.socyal.sc.persistence.cache;

import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class BnaCacheMapper {

	public CuisineEntity[] mapCuisinesArray(List<CuisineEntity> entities) {
		CuisineEntity[] cuisines = entities.toArray(new CuisineEntity[entities.size()]);
		return cuisines;
	}

	public SuggestionEntity[] mapSuggestionsArray(List<SuggestionEntity> entities) {
		SuggestionEntity[] suggestions = entities.toArray(new SuggestionEntity[entities.size()]);
		return suggestions;
	}
}
