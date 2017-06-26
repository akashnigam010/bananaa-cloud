package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.request.SearchMerchantByTagRequest;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.MerchantErrorCodeType;
import in.socyal.sc.persistence.entity.MerchantCuisineRatingEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.MerchantRatingEntity;
import in.socyal.sc.persistence.entity.MerchantSuggestionRatingEntity;
import in.socyal.sc.persistence.mapper.MerchantDaoMapper;

@Repository
public class MerchantDao {
	private static final String NAME = "name";
	private static final Integer RESULTS_PER_PAGE = 10;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	MerchantDaoMapper mapper;

	public MerchantDao() {
	}

	public MerchantDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public MerchantDto getMerchantDetailsByNameId(String nameId, MerchantFilterCriteria filter)
			throws BusinessException {
		MerchantDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq("nameId", nameId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		MerchantEntity entity = (MerchantEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new MerchantDto();
			mapper.map(entity, dto, filter);
		} else {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}

		return dto;
	}
	
	@SuppressWarnings("unchecked")
	public List<MerchantDto> getMerchantsByTag(SearchMerchantByTagRequest request) throws BusinessException {
		List<MerchantDto> merchants = new ArrayList<>();
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true);
		Criteria criteria = null;
		if (request.getType() == TagType.CUISINE) {
			criteria = sessionFactory.getCurrentSession().createCriteria(MerchantCuisineRatingEntity.class);
			criteria.add(Restrictions.eq("cuisine.id", request.getId()));
		} else if (request.getType() == TagType.SUGGESTION) {
			criteria = sessionFactory.getCurrentSession().createCriteria(MerchantSuggestionRatingEntity.class);
			criteria.add(Restrictions.eq("suggestion.id", request.getId()));
		} else {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		
		criteria.addOrder(Order.desc("rating"));
		int firstResult = ((request.getPage() - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		List<MerchantRatingEntity> entities = criteria.list();
		if (entities != null &&  entities.size() > 0) {
			mapper.mapByTag(entities, merchants, filter);
		} 
		return merchants;
	}

	public List<MerchantDto> searchActiveMerchant(String restaurantName, MerchantFilterCriteria filter)
			throws BusinessException {
		List<MerchantDto> merchantDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.ilike(NAME, restaurantName, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
		if (merchants != null && !merchants.isEmpty()) {
			merchantDtos = new ArrayList<>();
			mapper.map(merchants, merchantDtos, filter);
		}
		return merchantDtos;
	}
	
	public List<MerchantDto> searchMerchant(String restaurantName, MerchantFilterCriteria filter)
			throws BusinessException {
		List<MerchantDto> merchantDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.ilike(NAME, restaurantName, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
		if (merchants != null && !merchants.isEmpty()) {
			merchantDtos = new ArrayList<>();
			mapper.map(merchants, merchantDtos, filter);
		}
		return merchantDtos;
	}
}
