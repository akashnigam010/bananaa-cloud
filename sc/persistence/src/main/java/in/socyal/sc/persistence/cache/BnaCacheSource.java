package in.socyal.sc.persistence.cache;

import java.util.List;

import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;

public interface BnaCacheSource {
	List<CuisineEntity> getCuisines();
	void refreshCuisines();
	List<SuggestionEntity> getSuggestions();
	void refreshSuggestions();
	List<LocalityEntity> getLocalities();
	void refreshLocalities();
}
