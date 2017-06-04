package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.recommendation.request.RatingRequest;
import in.socyal.sc.api.recommendation.request.ReviewRequest;
import in.socyal.sc.api.type.error.RecommendationErrorCodeType;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.TrendingMerchantResultEntity;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.RecommendationDaoMapper;

@Repository
public class RecommendationDao {
	private static final Integer PAGE = 1;
	private static final Integer RESULTS_PER_PAGE = 5;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	RecommendationDaoMapper mapper;

	public RecommendationDao() {
	}

	public RecommendationDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveRating(RatingRequest request, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("dish.id", request.getId()));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		RecommendationEntity recommendation = (RecommendationEntity) criteria.uniqueResult();
		Calendar cal = Calendar.getInstance();
		if (recommendation == null) {
			recommendation = new RecommendationEntity(cal, cal);
			recommendation.setDish(new DishEntity(request.getId()));
			recommendation.setUser(new UserEntity(userId));
			recommendation.setRating(request.getRating().floatValue());
			recommendation.setIsActive(Boolean.TRUE);
		} else {
			recommendation.setRating(request.getRating().floatValue());
			recommendation.setUpdatedDateTime(cal);
		}
		session.saveOrUpdate(recommendation);
	}
	
	public void saveReview(ReviewRequest request, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("dish.id", request.getId()));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		RecommendationEntity recommendation = (RecommendationEntity) criteria.uniqueResult();
		Calendar cal = Calendar.getInstance();
		if (recommendation == null) {
			recommendation = new RecommendationEntity(cal, cal);
			recommendation.setDish(new DishEntity(request.getId()));
			recommendation.setUser(new UserEntity(userId));
			recommendation.setDescription(request.getDescription());
			recommendation.setIsActive(Boolean.TRUE);
		} else {
			recommendation.setDescription(request.getDescription());
			recommendation.setUpdatedDateTime(cal);
		}
		session.saveOrUpdate(recommendation);
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
		int firstResult = ((PAGE - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		criteria.setResultTransformer(Transformers.aliasToBean(TrendingMerchantResultEntity.class));
		List<TrendingMerchantResultEntity> result = (List<TrendingMerchantResultEntity>) criteria.list();
		mapper.map(result, response);
		return response;
	}

	@SuppressWarnings("unchecked")
	public Integer getMerchantRecommendationCount(Integer merchantId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.createAlias("dish", "d");
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		criteria.add(Restrictions.eq("d.isActive", Boolean.TRUE));
		criteria.add(Restrictions.eq("d.merchant.id", merchantId));
		List<RecommendationEntity> entities = criteria.list();
		if (entities == null) {
			return 0;
		}
		return entities.size();
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
		criteria.addOrder(Order.desc("updatedDateTime"));
		List<RecommendationEntity> result = (List<RecommendationEntity>) criteria.list();
		if (result != null) {
			for (RecommendationEntity entity : result) {
				RecommendationDto recommendation = new RecommendationDto();
				mapper.map(entity, recommendation);
				response.add(recommendation);
			}
			return response;
		} else {
			return Collections.emptyList();
		}		
	}

	public RecommendationDto getMyDishRecommendation(Integer userId, Integer dishId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("dish.id", dishId));
		RecommendationEntity entity = (RecommendationEntity) criteria.uniqueResult();
		RecommendationDto dto = null;
		if (entity != null) {
			dto = new RecommendationDto();
			mapper.map(entity, dto);
		}
		return dto;
	}

	public void addRecommendation(Integer userId, Integer dishId, String description) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("dish.id", dishId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		RecommendationEntity recommendation = (RecommendationEntity) criteria.uniqueResult();
		Calendar cal = Calendar.getInstance();
		if (recommendation == null) {
			recommendation = new RecommendationEntity(cal, cal);
			recommendation.setDish(new DishEntity(dishId));
			recommendation.setUser(new UserEntity(userId));
			recommendation.setIsActive(Boolean.TRUE);
		} else {
			if (StringUtils.isBlank(description)) {
				return;
			}
		}
		// Setting description field value to null even if the description from
		// UI is null/empty
		if (StringUtils.isNotBlank(description)) {
			recommendation.setDescription(description);
		}
		recommendation.setUpdatedDateTime(cal);
		session.saveOrUpdate(recommendation);
	}

	public void removeRecommendation(Integer recommendationId) throws BusinessException {
		Session session = sessionFactory.getCurrentSession();
		RecommendationEntity recommendation = (RecommendationEntity) session.get(RecommendationEntity.class,
				recommendationId);
		if (recommendation == null) {
			throw new BusinessException(RecommendationErrorCodeType.RCMDN_ID_NOT_FOUND);
		}

		recommendation.setIsActive(Boolean.FALSE);
		recommendation.setUpdatedDateTime(Calendar.getInstance());
		session.update(recommendation);
	}

	public void updateRecommendation(Integer recommendationId, String description) throws BusinessException {
		Session session = sessionFactory.getCurrentSession();
		RecommendationEntity recommendation = (RecommendationEntity) session.get(RecommendationEntity.class,
				recommendationId);
		if (recommendation == null || !recommendation.getIsActive()) {
			throw new BusinessException(RecommendationErrorCodeType.RCMDN_ID_NOT_FOUND);
		}

		recommendation.setDescription(description);
		recommendation.setUpdatedDateTime(Calendar.getInstance());
		session.saveOrUpdate(recommendation);
	}

	public Integer getDishRecommendationCount(Integer dishId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.add(Restrictions.eq("dish.id", dishId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		return criteria.list().size();
	}
}
