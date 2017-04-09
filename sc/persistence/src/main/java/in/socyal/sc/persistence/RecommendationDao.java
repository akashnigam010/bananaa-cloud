package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.type.error.DishErrorCodeType;
import in.socyal.sc.api.type.error.RecommendationErrorCodeType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.ReviewEntity;
import in.socyal.sc.persistence.entity.TrendingMerchantResultEntity;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.RecommendationDaoMapper;

@Repository
public class RecommendationDao {
	private static final Logger LOG = Logger.getLogger(RecommendationDao.class);
	private static final Integer RESULTS_PER_PAGE = 5;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	RecommendationDaoMapper mapper;
	@Autowired
	Clock clock;

	public RecommendationDao() {
	}

	public RecommendationDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<TrendingMerchantResultDto> getTrendingMerchants() {
		List<TrendingMerchantResultDto> response = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		criteria.createAlias("dish", "d");
		criteria.createAlias("d.merchant", "m");
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("m.id").as("recommendations"));
		projList.add(Projections.groupProperty("m.id").as("merchantId"));
		projList.add(Projections.property("d.merchant").as("merchant"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("recommendations"));
		criteria.setResultTransformer(Transformers.aliasToBean(TrendingMerchantResultEntity.class));
		List<TrendingMerchantResultEntity> result = (List<TrendingMerchantResultEntity>) criteria.list();
		mapper.map(result, response);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public List<RecommendationDto> getMyRecommendations(Integer userId, Integer merchantId, Integer page) {
		List<RecommendationDto> response = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		criteria.createAlias("user", "u");
		criteria.createAlias("dish", "d");
		criteria.createAlias("d.merchant", "m");
		criteria.add(Restrictions.eq("u.id", userId));
		criteria.add(Restrictions.eq("m.id", merchantId));
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		List<RecommendationEntity> result = (List<RecommendationEntity>) criteria.list();
		for (RecommendationEntity entity : result) {
			RecommendationDto recommendation = new RecommendationDto();
			mapper.map(entity, recommendation);
			response.add(recommendation);
		}
		return response;
	}
	
	public void addRecommendation(Integer userId, Integer dishId, String description) {
		RecommendationEntity recommendation = new RecommendationEntity();
		DishEntity dish = new DishEntity();
		dish.setId(dishId);
		recommendation.setDish(dish);
		UserEntity user = new UserEntity();
		user.setId(userId);
		recommendation.setUser(user);
		recommendation.setIsActive(Boolean.TRUE);
		recommendation.setCreatedDateTime(Calendar.getInstance());
		Integer recommendationId = (Integer) sessionFactory.getCurrentSession().save(recommendation);
		if (StringUtils.isNotBlank(description)) {
			ReviewEntity review = new ReviewEntity();
			review.setRecommendationId(recommendationId);
			review.setDescription(description);
			sessionFactory.getCurrentSession().save(review);
		}
	}
	
	public void removeRecommendation(Integer recommendationId) throws BusinessException {
		Session session = sessionFactory.getCurrentSession();
		RecommendationEntity recommendation = (RecommendationEntity) 
				session.get(RecommendationEntity.class, recommendationId);
		if (recommendation == null) {
			throw new BusinessException(RecommendationErrorCodeType.RCMDN_ID_NOT_FOUND);
		}
		
		recommendation.setIsActive(Boolean.FALSE);
		recommendation.setUpdatedDateTime(Calendar.getInstance());
		session.update(recommendation);
	}
	
	public void updateRecommendation(Integer recommendationId, Integer dishId, String description) 
			throws BusinessException {
		Session session = sessionFactory.getCurrentSession();
		RecommendationEntity recommendation = (RecommendationEntity) 
				session.get(RecommendationEntity.class, recommendationId);
		if (recommendation == null || !recommendation.getIsActive()) {
			throw new BusinessException(RecommendationErrorCodeType.RCMDN_ID_NOT_FOUND);
		}
		
		DishEntity dish = (DishEntity) session.get(DishEntity.class, dishId);
		if (dish == null) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		dish.setId(dishId);
		recommendation.setDish(dish);
		recommendation.setUpdatedDateTime(Calendar.getInstance());
		session.update(recommendation);
		Criteria criteria = session.createCriteria(ReviewEntity.class);
		criteria.add(Restrictions.eq("recommendationId", recommendationId));
		ReviewEntity review = (ReviewEntity) criteria.uniqueResult();
		review.setDescription(description);
		session.update(review);
	}
}
