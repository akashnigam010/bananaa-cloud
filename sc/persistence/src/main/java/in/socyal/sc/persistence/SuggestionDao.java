package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.mapper.SuggestionDaoMapper;

@Repository
public class SuggestionDao {
	private static final Logger LOG = Logger.getLogger(SuggestionDao.class);
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	SuggestionDaoMapper mapper;

	public SuggestionDao() {
	}

	public SuggestionDao(SessionFactory sessionFactory) {
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
}
