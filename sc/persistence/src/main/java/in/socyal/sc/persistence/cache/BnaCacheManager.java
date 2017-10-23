package in.socyal.sc.persistence.cache;

import java.util.List;

import in.socyal.sc.persistence.entity.CityEntity;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

public interface BnaCacheManager {
	List<CuisineEntity> getCuisines(int page, int resultsPerPage, String searchString);
	List<SuggestionEntity> getSuggestions(int page, int resultsPerPage, String searchString);
	List<CityEntity> getCities();
	void refreshCuisinesCache();
	void refreshSuggestionsCache();
	void refreshLocalitiesCache();
}
