package in.socyal.sc.persistence.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.entity.CityEntity;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class BnaCacheManagerImpl implements BnaCacheManager {
	@Autowired
	BnaCacheSource cacheSource;

	private List<CuisineEntity> searchCuisines(String searchString) {
		List<CuisineEntity> cuisines = cacheSource.getCuisines();
		if (StringUtils.isNotBlank(searchString)) {
			List<CuisineEntity> matches = new ArrayList<>();
			for (int i = 0; i < cuisines.size(); i++) {
				if (cuisines.get(i).getName().toLowerCase().contains(searchString.toLowerCase())) {
					matches.add(cuisines.get(i));
				}
			}
			return matches;
		}
		return cuisines;
	}

	private List<SuggestionEntity> searchSuggestions(String searchString) {
		List<SuggestionEntity> suggestions = cacheSource.getSuggestions();
		if (StringUtils.isNotBlank(searchString)) {
			List<SuggestionEntity> matches = new ArrayList<>();
			for (int i = 0; i < suggestions.size(); i++) {
				if (suggestions.get(i).getName().toLowerCase().contains(searchString.toLowerCase())) {
					matches.add(suggestions.get(i));
				}
			}
			return matches;
		}
		return suggestions;
	}

	@Override
	public List<CuisineEntity> getCuisines(int page, int resultsPerPage, String searchString) {
		List<CuisineEntity> cuisinesArray = searchCuisines(searchString);
		List<CuisineEntity> returnMap = new ArrayList<>();
		int startIndex = (page - 1) * resultsPerPage;
		int end = page * resultsPerPage;
		if (end > cuisinesArray.size()) {
			end = cuisinesArray.size();
		}
		for (int i = startIndex; i < end; i++) {
			returnMap.add(cuisinesArray.get(i));
		}
		return returnMap;
	}

	@Override
	public List<SuggestionEntity> getSuggestions(int page, int resultsPerPage, String searchString) {
		List<SuggestionEntity> suggestions = searchSuggestions(searchString);
		List<SuggestionEntity> returnMap = new ArrayList<>();
		int startIndex = (page - 1) * resultsPerPage;
		int end = page * resultsPerPage;
		if (end > suggestions.size()) {
			end = suggestions.size();
		}
		for (int i = startIndex; i < end; i++) {
			returnMap.add(suggestions.get(i));
		}
		return returnMap;
	}

	@Override
	public List<CityEntity> getCities() {
		return cacheSource.getCities();
	}

	@Override
	public void refreshCuisinesCache() {
		cacheSource.refreshCuisines();
	}

	@Override
	public void refreshSuggestionsCache() {
		cacheSource.refreshSuggestions();
	}

	@Override
	public void refreshLocalitiesCache() {
		cacheSource.refreshCities();
	}
}
