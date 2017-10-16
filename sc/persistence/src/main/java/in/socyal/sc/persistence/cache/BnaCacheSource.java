package in.socyal.sc.persistence.cache;

import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

public interface BnaCacheSource {
	CuisineEntity[] getCuisinesArray();
	SuggestionEntity[] getSuggestionsArray();
	LocalityEntity[] getLocalitiesArray();
}
