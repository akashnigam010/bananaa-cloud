package in.socyal.sc.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.persistence.entity.CheckinTaggedUserEntity;
import in.socyal.sc.persistence.entity.UserEntity;

@Repository
public class CheckinTaggedUserMappingDao {
	@Autowired
	SessionFactory sessionFactory;

	public CheckinTaggedUserMappingDao() {
	}

	public CheckinTaggedUserMappingDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void tagUsersToACheckin(Integer checkinId, List<Integer> taggedUsers) {
		for (Integer userId : taggedUsers) {
			CheckinTaggedUserEntity entity = new CheckinTaggedUserEntity();
			entity.setCheckinId(checkinId);
			UserEntity user = new UserEntity();
			user.setId(userId);
			entity.setUser(user);
			sessionFactory.getCurrentSession().save(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public List<CheckinTaggedUserEntity> getTaggedUsers(Integer checkinId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinTaggedUserEntity.class);
		criteria.add(Restrictions.eq("checkinId", checkinId));
		return criteria.list();
	}
}
