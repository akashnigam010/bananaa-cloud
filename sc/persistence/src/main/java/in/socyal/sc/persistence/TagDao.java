package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.mapper.TagDaoMapper;

@Repository
public class TagDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	TagDaoMapper mapper;

	public TagDao() {
	}

	public TagDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<SuggestionDto> fetchSuggestions(String match) {
		List<SuggestionDto> result = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		criteria.add(Restrictions.ilike("name", match, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<SuggestionEntity> suggestions = criteria.list();
		for (SuggestionEntity entity : suggestions) {
			SuggestionDto dto = new SuggestionDto();
			mapper.map(entity, dto);
			result.add(dto);
		}
		
		return result;
	}
	
	public List<String> fetchSuggestionsNamesOnly(String match) {
		List<String> result = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		criteria.add(Restrictions.ilike("name", match, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<SuggestionEntity> suggestions = criteria.list();
		for (SuggestionEntity entity : suggestions) {
			result.add(entity.getName());
		}
		
		return result;
	}
	
	public Map<String, SuggestionDto> getSuggestionCache() {
		Map<String, SuggestionDto> map = new HashMap<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		@SuppressWarnings("unchecked")
		List<SuggestionEntity> suggestions = criteria.list();
		for (SuggestionEntity entity : suggestions) {
			SuggestionDto dto = new SuggestionDto();
			mapper.map(entity, dto);
			map.put(dto.getNameId(), dto);
		}		
		return map;
	}
	
	public Map<String, CuisineDto> getCuisineCache() {
		Map<String, CuisineDto> map = new HashMap<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
		@SuppressWarnings("unchecked")
		List<CuisineEntity> suggestions = criteria.list();
		for (CuisineEntity entity : suggestions) {
			CuisineDto dto = new CuisineDto();
			mapper.map(entity, dto);
			map.put(dto.getNameId(), dto);
		}		
		return map;
	}
}
