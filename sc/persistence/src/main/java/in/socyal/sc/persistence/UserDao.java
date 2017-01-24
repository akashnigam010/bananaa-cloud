package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restfb.types.User;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.UserDaoMapper;

@Repository
public class UserDao {
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final int RESULTS_PER_PAGE = 15;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserDaoMapper mapper;
	@Autowired
	Clock clock;

	public UserDao() {
	}

	public UserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserDto fetchUser(Integer userId) {
		UserDto dto = null;
		UserEntity entity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userId);
		if (entity != null) {
			dto = new UserDto();
			mapper.map(entity, dto);
		}
		return dto;
	}
	
	public List<UserDto> fetchUsers(Integer resultsPerPage) {
		List<UserDto> userDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.setMaxResults(resultsPerPage);
		@SuppressWarnings("unchecked")
		List<UserEntity> users = criteria.list();
		for (UserEntity user : users) {
			UserDto dto = new UserDto();
			mapper.map(user, dto);
		}
		return userDtos;
	}

	public List<UserDto> discoverOtherUsersBySearchString(Integer currentUserId, String searchString, Integer resultsPerPage) {
		List<UserDto> userDtos = null;
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(discoverNewUsersQuery());
    	query.addEntity(UserEntity.class);
    	query.setInteger("current_user_id", currentUserId);
    	query.setString("search_string", "%"+searchString+"%");
    	query.setMaxResults(resultsPerPage);
		@SuppressWarnings("unchecked")
		List<UserEntity> users = (List<UserEntity>) query.list();
		if (users != null && !users.isEmpty()) {
			userDtos = new ArrayList<>();
			mapper.map(users, userDtos);
		}

		return userDtos;
	}

	public List<UserDto> fetchUsersByPage(int page) {
		List<UserDto> userDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<UserEntity> users = (List<UserEntity>) criteria.list();
		if (users != null && !users.isEmpty()) {
			userDtos = new ArrayList<>();
			mapper.map(users, userDtos);
		}

		return userDtos;
	}

	/**
	 * Save or update user details. <br>
	 * <br>
	 * If user doesn't exist, a new user is created.<br>
	 * If user already exists, its FB access token is compared with the passed
	 * access token, and updated accordingly.<br>
	 * This is done to update the token if it was expired already
	 * 
	 * @param user
	 * @param fbAccessToken
	 * @return
	 */
	public UserDto saveOrUpdate(User user, String fbAccessToken) {
		UserDto userDto = new UserDto();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("facebookId", user.getId()));
		UserEntity entity = (UserEntity) criteria.uniqueResult();

		if (entity == null) {
			entity = new UserEntity();
			mapper.map(user, entity, fbAccessToken);
			sessionFactory.getCurrentSession().save(entity);
		} else if (StringUtils.equals(entity.getFacebookId(), fbAccessToken)) {
			entity.setFacebookId(fbAccessToken);
			entity.setUpdatedDateTime(clock.cal());
			sessionFactory.getCurrentSession().save(entity);
		}
		mapper.map(entity, userDto);
		return userDto;
	}
	
	public void saveRegistrationIdForUser(Integer userId, String registrationId) {
		Session session = sessionFactory.getCurrentSession();
		UserEntity user = (UserEntity) session.load(UserEntity.class, userId);
		user.setUpdatedDateTime(clock.cal());
		user.setRegistrationId(registrationId);
		session.update(user);
	}
	
    private String discoverNewUsersQuery() {
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT * FROM Socyal.USER ");
    	query.append("where FIRST_NAME LIKE :search_string OR LAST_NAME LIKE :search_string ");
    	query.append("AND ID NOT IN ");
    	query.append("(SELECT USER_ID FROM Socyal.USER_FOLLOWER_MAPPING WHERE FOLLOWER_USER_ID = :current_user_id) ");
    	return query.toString();
    }
}
