package in.socyal.sc.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.CheckinUserLikeEntity;

@Repository
public class CheckinUserLikeMappingDao {
	@Autowired SessionFactory sessionFactory;

	public CheckinUserLikeMappingDao() {
	}

	public CheckinUserLikeMappingDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void likeACheckin(Integer checkinId, Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinUserLikeEntity.class);
    	criteria.add(Restrictions.eq("userId", userId));
    	criteria.add(Restrictions.eq("checkinId", checkinId));
    	@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
    	
    	if (result.size() == 0) {
    		CheckinUserLikeEntity entity = new CheckinUserLikeEntity();
    		entity.setCheckinId(checkinId);
    		entity.setUserId(userId);
    		sessionFactory.getCurrentSession().save(entity);
    	}
	}
	
//	public Boolean isCurrentCheckinLiked(Integer checkinId, Integer userId) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinUserLikeEntity.class);
//    	criteria.add(Restrictions.eq("userId", userId));
//    	criteria.add(Restrictions.eq("checkinId", checkinId));
//    	@SuppressWarnings("unchecked")
//		List<CheckinEntity> result = criteria.list();
//    	return result.size() > 0 ? Boolean.TRUE : Boolean.FALSE;
//	}
	
	public Integer fetchLikeCount(Integer checkinId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinUserLikeEntity.class);
    	criteria.add(Restrictions.eq("checkinId", checkinId));
    	@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
    	return result.size();
	}

	public void unLikeACheckin(Integer checkinId, Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinUserLikeEntity.class);
    	criteria.add(Restrictions.eq("userId", userId));
    	criteria.add(Restrictions.eq("checkinId", checkinId));
    	@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
    	
    	if (result.size() > 0) {
    		Criteria criteria2 = sessionFactory.getCurrentSession().createCriteria(CheckinUserLikeEntity.class);
    		criteria2.add(Restrictions.eq("userId", userId));
        	criteria2.add(Restrictions.eq("checkinId", checkinId));
        	CheckinUserLikeEntity entity = (CheckinUserLikeEntity) criteria2.uniqueResult();
        	sessionFactory.getCurrentSession().delete(entity);
    	}		
	}
}
