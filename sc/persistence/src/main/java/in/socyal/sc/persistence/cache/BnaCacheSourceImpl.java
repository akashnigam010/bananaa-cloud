package in.socyal.sc.persistence.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.DishDao;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class BnaCacheSourceImpl implements BnaCacheSource {
	@Autowired
	DishDao dishDao;
	@Autowired
	BnaCacheMapper mapper;

	private CuisineEntity[] cuisinesArray = null;
	private SuggestionEntity[] suggestionsArray = null;

	@Override
	public CuisineEntity[] getCuisinesArray() {
		if (cuisinesArray == null) {
			setCuisinesArray();
		}
		return cuisinesArray;
	}

	@Override
	public SuggestionEntity[] getSuggestionsArray() {
		if (suggestionsArray == null) {
			setSuggestionsArray();
		}
		return suggestionsArray;
	}

	@Override
	public LocalityEntity[] getLocalitiesArray() {
		// TODO: add implementation
		return null;
	}

	private void setCuisinesArray() {
		List<CuisineEntity> cuisines = dishDao.getAllCuisines();
		cuisinesArray = mapper.mapCuisinesArray(cuisines);
	}

	private void setSuggestionsArray() {
		List<SuggestionEntity> suggestions = dishDao.getAllSuggestions();
		suggestionsArray = mapper.mapSuggestionsArray(suggestions);
	}
}
