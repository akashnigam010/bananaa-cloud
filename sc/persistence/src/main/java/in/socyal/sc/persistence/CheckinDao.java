package in.socyal.sc.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.mapper.CheckinDaoMapper;

@Repository
public class CheckinDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired CheckinDaoMapper mapper;
 
    public CheckinDao() {
    }
     
    public CheckinDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Transactional
    public Integer confirmCheckin(CheckinDetailsDto checkinDetails) {
    	CheckinEntity entity = new CheckinEntity();
    	mapper.map(checkinDetails, entity);
    	Integer checkinId = (Integer) sessionFactory.getCurrentSession().save(entity);
    	return checkinId;
    }
    
    @Transactional
    public Integer getPreviousCheckins(Integer userId, Integer merchantId) {
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
    	criteria.add(Restrictions.eq("userId", userId));
    	criteria.add(Restrictions.eq("merchantId", merchantId));
    	criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
    	@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
    	return result.size();
    }
}
