package in.socyal.sc.persistence.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.persistence.DishDao;
import in.socyal.sc.persistence.LocationDao;
import in.socyal.sc.persistence.entity.CityEntity;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

@Component
public class BnaCacheSourceImpl implements BnaCacheSource {
	@Autowired
	DishDao dishDao;
	@Autowired
	LocationDao locationDao;

	private List<CuisineEntity> cuisines = null;
	private List<SuggestionEntity> suggestions = null;
	private List<CityEntity> cities = null;

	@Override
	public List<CuisineEntity> getCuisines() {
		if (cuisines == null) {
			refreshCuisines();
		}
		return cuisines;
	}

	@Override
	public List<SuggestionEntity> getSuggestions() {
		if (suggestions == null) {
			refreshSuggestions();
		}
		return suggestions;
	}

	@Override
	public List<CityEntity> getCities() {
		if (cities == null) {
			refreshCities();
		}
		return cities;
	}

	@Override
	public void refreshCuisines() {
		cuisines = dishDao.getAllCuisines();
	}

	@Override
	public void refreshSuggestions() {
		suggestions = dishDao.getAllSuggestions();
	}

	@Override
	public void refreshCities() {
		cities = locationDao.getCities();
	}
}
