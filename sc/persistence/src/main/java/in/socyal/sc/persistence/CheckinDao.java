package in.socyal.sc.persistence;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.mapper.CheckinDaoMapper;

@Repository
public class CheckinDao {
	private static final Logger LOG = Logger.getLogger(CheckinDao.class);
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
    	criteria.add(Restrictions.eq("merchant.id", merchantId));
    	criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
    	@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
    	return result.size();
    }
    
    @Transactional
    public void cancelCheckin(Integer checkinId) {
    	CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
    	if (checkin != null) {
    		checkin.setStatus(CheckinStatusType.CANCELLED);
    		checkin.setUpdatedDateTime(Calendar.getInstance());
        	sessionFactory.getCurrentSession().update(checkin);
    	} else {
    		LOG.error("Checkin entity not found for checkinID:" + checkinId);
    	}
    }

    @Transactional
	public CheckinDto getCheckin(Integer checkinId) {
		CheckinDto checkinDto = null;
		CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
		if (checkin != null) {
			checkinDto = new CheckinDto();
			mapper.map(checkin, checkinDto);
		}
		return checkinDto;
		
	}
}
