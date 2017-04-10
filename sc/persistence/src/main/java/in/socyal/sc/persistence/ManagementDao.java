package in.socyal.sc.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.SearchRequest;
import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.AddRequest;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.api.type.error.MerchantErrorCodeType;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.mapper.ManagementDaoMapper;

@Repository
public class ManagementDao {
	private static final String NAME = "name";
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ManagementDaoMapper mapper;

	public ManagementDao() {
	}

	public ManagementDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addItem(AddItemRequest request) throws BusinessException {
		MerchantEntity merchant = getMerchantById(request.getMerchantId());
		DishEntity entity = mapper.map(request, merchant);
		sessionFactory.getCurrentSession().save(entity);
	}
	
	public void addCuisine(AddRequest request) {
		CuisineEntity entity = new CuisineEntity();
		entity.setName(request.getName());
		sessionFactory.getCurrentSession().save(entity);
	}

	public void addSuggestion(AddRequest request) {
		SuggestionEntity entity = new SuggestionEntity();
		entity.setName(request.getName());
		sessionFactory.getCurrentSession().save(entity);
	}

	public List<CuisineDto> getCuisines(SearchRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
		criteria.add(Restrictions.ilike(NAME, request.getSearchString(), MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<CuisineEntity> entities = criteria.list();
		return mapper.mapCuisine(entities);
	}
	
	public List<SuggestionDto> getSuggestions(SearchRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		criteria.add(Restrictions.ilike(NAME, request.getSearchString(), MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<SuggestionEntity> entities = criteria.list();
		return mapper.mapSuggestion(entities);
	}
	
	private MerchantEntity getMerchantById(Integer id) throws BusinessException {
		MerchantEntity merchant = (MerchantEntity) sessionFactory.getCurrentSession().get(MerchantEntity.class, id);
		if (merchant == null) {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}
		return merchant;
	}
}
