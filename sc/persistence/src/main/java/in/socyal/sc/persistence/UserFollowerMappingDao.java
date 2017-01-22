package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.entity.UserFollowerMappingEntity;
import in.socyal.sc.persistence.mapper.UserFollowerDaoMapper;

@Repository
public class UserFollowerMappingDao {
	private static final int RESULTS_PER_PAGE = 15;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserFollowerDaoMapper mapper;

	public UserFollowerMappingDao() {
	}

	public UserFollowerMappingDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void follow(Integer userId, Integer followerUserId) {
		UserFollowerMappingEntity entity = new UserFollowerMappingEntity();
		UserEntity user = new UserEntity();
		user.setId(userId);
		entity.setUser(user);
		UserEntity followerUser = new UserEntity();
		followerUser.setId(followerUserId);
		entity.setFollowerUser(followerUser);
		sessionFactory.getCurrentSession().save(entity);
	}
	
	public Boolean isAlreadyFollowing(Integer userId, Integer followerUserId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserFollowerMappingEntity.class);
    	criteria.add(Restrictions.eq("user.id", userId));
    	criteria.add(Restrictions.eq("followerUser.id", followerUserId));
    	@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
    	return result.size() > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	public void unFollow(Integer userId, Integer followerUserId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserFollowerMappingEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
    	criteria.add(Restrictions.eq("followerUser.id", followerUserId));
    	UserFollowerMappingEntity entity = (UserFollowerMappingEntity) criteria.uniqueResult();
    	sessionFactory.getCurrentSession().delete(entity);
	}

	public List<UserDto> fetchMyFriendsByPage(Integer page, Integer currentUserId) {
		List<UserDto> userDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserFollowerMappingEntity.class);
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		criteria.add(Restrictions.eq("followerUser.id", currentUserId));
		@SuppressWarnings("unchecked")
		List<UserFollowerMappingEntity> users = (List<UserFollowerMappingEntity>) criteria.list();
		if (users != null && !users.isEmpty()) {
			userDtos = new ArrayList<>();
			mapper.map(users, userDtos);
		}

		return userDtos;
	}
}
