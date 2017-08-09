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
import in.socyal.sc.persistence.entity.MerchantSuggestionRatingEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.rating.engine.RatingUtils;

@Repository
public class SuggestionRatingEngine {
	private static final Float MAX_RATING = 5.0f;

	@Autowired
	DishDao dishDao;
	@Autowired
	SessionFactory sessionFactory;

	public SuggestionRatingEngine() {
	}

	public SuggestionRatingEngine(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public void rateRestaurantForTags(Integer merchantId) {
		List<SuggestionEntity> tags = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class).list();
		if (tags != null & tags.size() > 0) {
			Calendar cal = null;
			Criteria mtrCriteria = null;
			Criteria dishCriteria = null;
			for (SuggestionEntity tag : tags) {
				cal = Calendar.getInstance();
				dishCriteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
				dishCriteria.add(Restrictions.eq("merchant.id", merchantId));
				//dishCriteria.add(Restrictions.gt("rating", 0.0f));
				dishCriteria.createAlias("suggestions", "suggestion");
				dishCriteria.add(Restrictions.eq("suggestion.id", tag.getId()));
				List<DishEntity> dishes = dishCriteria.list();
				if (dishes != null && dishes.size() > 0) {
					mtrCriteria = sessionFactory.getCurrentSession().createCriteria(MerchantSuggestionRatingEntity.class);
					mtrCriteria.add(Restrictions.eq("merchant.id", merchantId));
					mtrCriteria.add(Restrictions.eq("suggestion.id", tag.getId()));
					MerchantSuggestionRatingEntity mtrEntity = (MerchantSuggestionRatingEntity) mtrCriteria.uniqueResult();
					if (mtrEntity == null) {
						mtrEntity = new MerchantSuggestionRatingEntity(cal, cal);
						mtrEntity.setSuggestion(tag);
						mtrEntity.setMerchant(dishes.get(0).getMerchant());
						mtrEntity.setRating(getTagRating(dishes));
						mtrEntity.setDishCount(dishes.size());
					} else {
						mtrEntity.setRating(getTagRating(dishes));
						mtrEntity.setDishCount(dishes.size());
						mtrEntity.setUpdatedDateTime(cal);
					}
					sessionFactory.getCurrentSession().saveOrUpdate(mtrEntity);
				}
			}
		}
	}

	private Float getTagRating(List<DishEntity> dishes) {
		float rating = 0.0f;
		int ratedDishes = 0;
		for (DishEntity entity : dishes) {
			if (entity.getRating() > 0) {
				rating += entity.getRating();
				ratedDishes++;
			}
		}
		if (ratedDishes > 0) {
			rating = (rating / ratedDishes) + RatingUtils.getDishCountRating(dishes);
			if (rating > MAX_RATING) {
				rating = MAX_RATING;
			}
		}		
		return rating;
	}
}
