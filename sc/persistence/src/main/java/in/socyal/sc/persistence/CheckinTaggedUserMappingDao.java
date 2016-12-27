package in.socyal.sc.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;

@Repository
public class CheckinTaggedUserMappingDao {
	@Autowired SessionFactory sessionFactory;
 
    public CheckinTaggedUserMappingDao() {
    }
     
    public CheckinTaggedUserMappingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public void tagUsersToACheckin(Integer checkinId, List<Integer> taggedUsers) {
    	for (Integer userId : taggedUsers) {
    		CheckinTaggedUserEntity entity = new CheckinTaggedUserEntity();
    		entity.setCheckinId(checkinId);
    		entity.setUserId(userId);
    		sessionFactory.getCurrentSession().save(entity);
    	}
    }
}
