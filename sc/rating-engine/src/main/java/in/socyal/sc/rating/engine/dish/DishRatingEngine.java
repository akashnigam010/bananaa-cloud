package in.socyal.sc.rating.engine.dish;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.persistence.DishDao;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.rating.engine.RatingUtils;

@Repository
public class DishRatingEngine {
	private static final Integer RESULTS_PER_PAGE = 5;
	private static final Float USER_CRED_WEIGHTAGE = 0.5f;
	private static final Float DISH_TRIAL_WEIGHTAGE = 0.5f;

	@Autowired
	DishDao dishDao;
	@Autowired
	SessionFactory sessionFactory;

	public DishRatingEngine() {
	}

	public DishRatingEngine(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void rateRestaurantDishes(Integer merchantId) {
		List<DishEntity> dishes = null;
		Integer page = 1;
		Float triedVal = null;
		Float userRatingVal = null;
		Float finalRating = null;
		do {
			dishes = getAllItemEntities(merchantId, page, RESULTS_PER_PAGE);
			for (DishEntity entity : dishes) {
				triedVal = RatingUtils.getTriedValue(entity);
				userRatingVal = RatingUtils.getUserRatedValue(entity);
				finalRating = triedVal * DISH_TRIAL_WEIGHTAGE + userRatingVal * USER_CRED_WEIGHTAGE;
				entity.setRating(finalRating);
				entity.setUpdatedDateTime(Calendar.getInstance());
				sessionFactory.getCurrentSession().saveOrUpdate(entity);
			}
			sessionFactory.getCurrentSession().flush();
			page++;
		} while (!dishes.isEmpty());
	}

	@SuppressWarnings("unchecked")
	private List<DishEntity> getAllItemEntities(Integer merchantId, Integer page, Integer resultsPerPage) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		return criteria.list();
	}

}
