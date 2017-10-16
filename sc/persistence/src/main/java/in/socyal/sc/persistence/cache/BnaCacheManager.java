package in.socyal.sc.persistence.cache;

import java.util.List;
import java.util.Map;

import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

public interface BnaCacheManager {
	Map<Integer, CuisineEntity> getCuisinesMap(int page, int resultsPerPage);

	List<CuisineEntity> getCuisines(int page, int resultsPerPage);

	Map<Integer, SuggestionEntity> getSuggestionsMap(int page, int resultsPerPage);

	List<SuggestionEntity> getSuggestions(int page, int resultsPerPage);

	Map<Integer, LocalityEntity> getLocalities();

	void refreshCuisinesCache();

	void refreshSuggestionsCache();

	void refreshLocalitiesCache();
}
