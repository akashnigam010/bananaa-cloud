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
import in.socyal.sc.api.items.dto.DishDetailsResultDto;
import in.socyal.sc.api.items.dto.PopularDishesResultDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.persistence.entity.DishDetailsResult;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.PopularDishesResult;
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
	public List<PopularDishesResultDto> getPopularDishesOfMerchant(Integer merchantId, Integer page, Integer resultsPerPage) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		//criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
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
		criteria.setResultTransformer(Transformers.aliasToBean(PopularDishesResult.class));
		List<PopularDishesResult> result = (List<PopularDishesResult>) criteria.list();
		return mapper.map(result);
	}
	
	public DishDetailsResultDto getItemDetails(String dishNameId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.createAlias("dish", "d");
		criteria.createAlias("d.merchant", "m");
		criteria.add(Restrictions.eq("d.nameId", dishNameId));
		//criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("id").as("recommendations"));
		projList.add(Projections.groupProperty("dish.id"));
		projList.add(Projections.property("dish").as("dish"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("recommendations"));
		criteria.setResultTransformer(Transformers.aliasToBean(DishDetailsResult.class));
		DishDetailsResult result = (DishDetailsResult) criteria.uniqueResult();
		return mapper.map(result);
	}
	
	@SuppressWarnings("unchecked")
	public List<RecommendationDto> getReviews(Integer dishId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("dish.id", dishId));
		criteria.add(Restrictions.isNotNull("description"));
		List<RecommendationEntity> result = (List<RecommendationEntity>) criteria.list();
		return rcmdnMapper.map(result, Boolean.TRUE);
	}
}
