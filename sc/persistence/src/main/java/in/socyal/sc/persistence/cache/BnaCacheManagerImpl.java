package in.socyal.sc.persistence.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class BnaCacheManagerImpl implements BnaCacheManager {
	@Autowired
	BnaCacheSource cacheSource;

	@Override
	public Map<Integer, CuisineEntity> getCuisinesMap(int page, int resultsPerPage) {
		CuisineEntity[] cuisinesArray = cacheSource.getCuisinesArray();
		Map<Integer, CuisineEntity> returnMap = new HashMap<>();
		int startIndex = (page - 1) * resultsPerPage;
		int end = page * resultsPerPage;
		if (end > cuisinesArray.length) {
			end = cuisinesArray.length;
		}
		for (int i = startIndex; i < end; i++) {
			returnMap.put(cuisinesArray[i].getId(), cuisinesArray[i]);
		}
		return returnMap;
	}

	@Override
	public List<CuisineEntity> getCuisines(int page, int resultsPerPage) {
		return new ArrayList<CuisineEntity>(getCuisinesMap(page, resultsPerPage).values());
	}

	@Override
	public Map<Integer, SuggestionEntity> getSuggestionsMap(int page, int resultsPerPage) {
		SuggestionEntity[] suggestionsArray = cacheSource.getSuggestionsArray();
		Map<Integer, SuggestionEntity> returnMap = new HashMap<>();
		int startIndex = (page - 1) * resultsPerPage;
		int end = page * resultsPerPage;
		if (end > suggestionsArray.length) {
			end = suggestionsArray.length;
		}
		for (int i = startIndex; i < end; i++) {
			returnMap.put(suggestionsArray[i].getId(), suggestionsArray[i]);
		}
		return returnMap;
	}

	@Override
	public List<SuggestionEntity> getSuggestions(int page, int resultsPerPage) {
		return new ArrayList<SuggestionEntity>(getSuggestionsMap(page, resultsPerPage).values());
	}

	@Override
	public Map<Integer, LocalityEntity> getLocalities() {
		return null;
	}

	@Override
	public void refreshCuisinesCache() {

	}

	@Override
	public void refreshSuggestionsCache() {
	}

	@Override
	public void refreshLocalitiesCache() {
	}
}
