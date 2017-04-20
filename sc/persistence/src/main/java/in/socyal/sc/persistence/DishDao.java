package in.socyal.sc.persistence;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.items.dto.DishResultDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.type.error.DishErrorCodeType;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.DishResult;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.mapper.DishDaoMapper;
import in.socyal.sc.persistence.mapper.RecommendationDaoMapper;

@Repository
public class DishDao {
	private static final String NAME = "name";
	@Autowired
	DishDaoMapper mapper;
	@Autowired
	RecommendationDaoMapper rcmdnMapper;
	@Autowired
	SessionFactory sessionFactory;

	public DishDao() {
	}

	public DishDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean isDishExists(Integer dishId) {
		DishEntity dish = (DishEntity) sessionFactory.getCurrentSession().get(DishEntity.class, dishId);
		return dish == null ? false : true;
	}

	public List<DishDto> searchDishAtARestaurant(String searchString, Integer merchantId) {
		List<DishDto> dishDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.ilike(NAME, searchString, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		@SuppressWarnings("unchecked")
		List<DishEntity> dishes = (List<DishEntity>) criteria.list();
		if (dishes != null && !dishes.isEmpty()) {
			dishDtos = mapper.map(dishes, null);
			return dishDtos;
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public List<DishResultDto> getPopularDishesOfMerchant(Integer merchantId, Integer page, Integer resultsPerPage) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.createAlias("dish", "d");
		criteria.createAlias("d.merchant", "m");
		criteria.add(Restrictions.eq("m.id", merchantId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("id").as("recommendations"));
		projList.add(Projections.groupProperty("dish.id"));
		projList.add(Projections.property("dish").as("dish"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("recommendations"));
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		criteria.setResultTransformer(Transformers.aliasToBean(DishResult.class));
		List<DishResult> result = (List<DishResult>) criteria.list();
		MerchantFilterCriteria filterCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		return mapper.mapDishResults(result, filterCriteria);
	}

//	public DishResultDto getItemDetails(String merchantNameId, String dishNameId) throws BusinessException {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
//		criteria.createAlias("dish", "d");
//		criteria.createAlias("d.merchant", "m");
//		criteria.add(Restrictions.eq("m.nameId", merchantNameId));
//		criteria.add(Restrictions.eq("d.nameId", dishNameId));
//		criteria.add(Restrictions.eq("d.isActive", Boolean.TRUE));
//		ProjectionList projList = Projections.projectionList();
//		projList.add(Projections.count("id").as("recommendations"));
//		projList.add(Projections.groupProperty("dish.id"));
//		projList.add(Projections.property("dish").as("dish"));
//		criteria.setProjection(projList);
//		criteria.addOrder(Order.desc("recommendations"));
//		criteria.setResultTransformer(Transformers.aliasToBean(DishResult.class));
//		DishResult result = (DishResult) criteria.uniqueResult();
//		if (result == null) {
//			throw new BusinessException(DishErrorCodeType.DISH_DETAILS_NOT_FOUND);
//		}
//		MerchantFilterCriteria filterCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
//		return mapper.map(result, filterCriteria);
//	}
	
	public DishDto getItemDetails(String merchantNameId, String dishNameId) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.createAlias("merchant", "m");
		criteria.createAlias("recommendations", "r");
		criteria.add(Restrictions.eq("m.nameId", merchantNameId));
		criteria.add(Restrictions.eq("nameId", dishNameId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		criteria.add(Restrictions.eq("r.isActive", Boolean.TRUE));
		DishEntity entity = (DishEntity) criteria.uniqueResult();
		if (entity == null) {
			throw new BusinessException(DishErrorCodeType.DISH_DETAILS_NOT_FOUND);
		}
		MerchantFilterCriteria filterCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		return mapper.map(entity, filterCriteria);
	}

//	@SuppressWarnings("unchecked")
//	public List<RecommendationDto> getReviews(Integer dishId) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
//		criteria.createAlias("dish", "d");
//		criteria.createAlias("user", "u");
//		criteria.createAlias("(select count(*) recommendation, user_id from bna.recommendation r group by user_id)", "s");
//		criteria.add(Restrictions.eq("d.id", dishId));
//		criteria.add(Restrictions.isNotNull("description"));
//		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
//		criteria.add(Restrictions.eq("u.id", "s.user_id"));
//		ProjectionList projList = Projections.projectionList();
//		//projList.add(Projections.property("recommendation").as("recommendation"));
//		projList.add(Projections.property("user").as("user"));
//		projList.add(Projections.property("s").as("recommendationCount"));
//		criteria.setProjection(projList);
//		criteria.setResultTransformer(Transformers.aliasToBean(ReviewResult.class));
//		List<ReviewResult> result = (List<ReviewResult>) criteria.list();
//		return null;
//	}
}
