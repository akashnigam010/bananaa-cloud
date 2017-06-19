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
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.MerchantCuisineRatingEntity;

@Repository
public class CuisineRatingEngine {

	@Autowired
	DishDao dishDao;
	@Autowired
	SessionFactory sessionFactory;

	public CuisineRatingEngine() {
	}

	public CuisineRatingEngine(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public void rateRestaurantForCuisines(Integer merchantId) {
		List<CuisineEntity> cuisines = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class).list();
		if (cuisines != null & cuisines.size() > 0) {
			Calendar cal = null;
			Criteria mcrCriteria = null;
			Criteria dishCriteria = null;
			for (CuisineEntity cuisine : cuisines) {
				cal = Calendar.getInstance();
				dishCriteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
				dishCriteria.add(Restrictions.eq("merchant.id", merchantId));
				//dishCriteria.add(Restrictions.gt("rating", 0.0f));
				dishCriteria.createAlias("cuisines", "cuisine");
				dishCriteria.add(Restrictions.eq("cuisine.id", cuisine.getId()));
				List<DishEntity> dishes = dishCriteria.list();
				if (dishes != null && dishes.size() > 0) {
					mcrCriteria = sessionFactory.getCurrentSession().createCriteria(MerchantCuisineRatingEntity.class);
					mcrCriteria.add(Restrictions.eq("merchant.id", merchantId));
					mcrCriteria.add(Restrictions.eq("cuisine.id", cuisine.getId()));
					MerchantCuisineRatingEntity mcrEntity = (MerchantCuisineRatingEntity) mcrCriteria.uniqueResult();
					if (mcrEntity == null) {
						mcrEntity = new MerchantCuisineRatingEntity(cal, cal);
						mcrEntity.setCuisine(cuisine);
						mcrEntity.setMerchant(dishes.get(0).getMerchant());
						mcrEntity.setDishCount(dishes.size());
						mcrEntity.setRating(getCuisineRating(dishes));
					} else {
						mcrEntity.setDishCount(dishes.size());
						mcrEntity.setRating(getCuisineRating(dishes));
						mcrEntity.setUpdatedDateTime(cal);
					}
					sessionFactory.getCurrentSession().saveOrUpdate(mcrEntity);
				}
			}
		}
	}

	private Float getCuisineRating(List<DishEntity> dishes) {
		float totalRating = 0.0f;
		for (DishEntity entity : dishes) {
			totalRating += entity.getRating();
		}
		return totalRating / dishes.size();
	}
}
