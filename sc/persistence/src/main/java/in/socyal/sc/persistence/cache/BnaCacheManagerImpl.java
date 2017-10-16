package in.socyal.sc.persistence.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class BnaCacheManagerImpl implements BnaCacheManager {
	@Autowired
	BnaCacheSource cacheSource;
	
	private CuisineEntity[] searchCuisines(String searchString) {
		CuisineEntity[] cuisinesArray = cacheSource.getCuisinesArray();
		if (StringUtils.isNotBlank(searchString)) {
			List<CuisineEntity> matches = new ArrayList<>();
			for (int i = 0; i < cuisinesArray.length; i++) {
				if (cuisinesArray[i].getName().toLowerCase().contains(searchString.toLowerCase())) {
					matches.add(cuisinesArray[i]);
				}
			}
			if (matches.size() > 0) {
				return matches.toArray(new CuisineEntity[matches.size()]);
			}			
		}
		return cuisinesArray;
	}
	
	private SuggestionEntity[] searchSuggestions(String searchString) {
		SuggestionEntity[] suggestionsArray = cacheSource.getSuggestionsArray();
		if (StringUtils.isNotBlank(searchString)) {
			List<SuggestionEntity> matches = new ArrayList<>();
			for (int i = 0; i < suggestionsArray.length; i++) {
				if (suggestionsArray[i].getName().toLowerCase().contains(searchString.toLowerCase())) {
					matches.add(suggestionsArray[i]);
				}
			}
			if (matches.size() > 0) {
				return matches.toArray(new SuggestionEntity[matches.size()]);
			}			
		}
		return suggestionsArray;
	}

	@Override
	public List<CuisineEntity> getCuisines(int page, int resultsPerPage, String searchString) {
		CuisineEntity[] cuisinesArray = searchCuisines(searchString);
		List<CuisineEntity> returnMap = new ArrayList<>();
		int startIndex = (page - 1) * resultsPerPage;
		int end = page * resultsPerPage;
		if (end > cuisinesArray.length) {
			end = cuisinesArray.length;
		}
		for (int i = startIndex; i < end; i++) {
			returnMap.add(cuisinesArray[i]);
		}
		return returnMap;
	}

	@Override
	public List<SuggestionEntity> getSuggestions(int page, int resultsPerPage, String searchString) {
		SuggestionEntity[] suggestionsArray = searchSuggestions(searchString);
		List<SuggestionEntity> returnMap = new ArrayList<>();
		int startIndex = (page - 1) * resultsPerPage;
		int end = page * resultsPerPage;
		if (end > suggestionsArray.length) {
			end = suggestionsArray.length;
		}
		for (int i = startIndex; i < end; i++) {
			returnMap.add(suggestionsArray[i]);
		}
		return returnMap;
	}

	@Override
	public List<LocalityEntity> getLocalities() {
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
