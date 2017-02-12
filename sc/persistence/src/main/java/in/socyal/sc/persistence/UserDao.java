package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
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
	@Autowired SessionFactory sessionFactory;
	@Autowired UserDaoMapper mapper;
	@Autowired Clock clock;

	public UserDao() {
	}

	public UserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * This method takes userId as request and responds back with UserDto object
	 * @param userId
	 * @return
	 */
	public UserDto fetchUser(Integer userId) {
		UserDto dto = null;
		UserEntity entity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userId);
		if (entity != null) {
			dto = new UserDto();
			mapper.map(entity, dto);
		}
		return dto;
	}
	
	/**
	 * This method takes list of userIds as request to give Collection of UserDtos as response
	 * @param userIds
	 * @return
	 */
	public List<UserDto> fetchUsersByIds(List<Integer> userIds) {
		List<UserDto> userDtos = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.in("id", userIds));
		@SuppressWarnings("unchecked")
		List<UserEntity> users = criteria.list();
		for (UserEntity user : users) {
			UserDto dto = new UserDto();
			mapper.map(user, dto);
			userDtos.add(dto);
		}
		return userDtos;
	}
	
	/**
	 * This method takes resultsPerPageRequest to respond back with those many user dto objects
	 * @param resultsPerPage
	 * @return
	 */
	public List<UserDto> fetchUsers(String searchString, Integer resultsPerPage) {
		List<UserDto> userDtos = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		Criterion firstNameCriteria = Restrictions.ilike("firstName", searchString, MatchMode.ANYWHERE);
		Criterion lastNameCriteria = Restrictions.ilike("lastName", searchString, MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(firstNameCriteria, lastNameCriteria));
		criteria.setMaxResults(resultsPerPage);
		@SuppressWarnings("unchecked")
		List<UserEntity> users = criteria.list();
		for (UserEntity user : users) {
			UserDto dto = new UserDto();
			mapper.map(user, dto);
			userDtos.add(dto);
		}
		return userDtos;
	}

	/**
	 * This method is used to search users based on a search string
	 * @param currentUserId
	 * @param searchString
	 * @param resultsPerPage
	 * @return
	 */
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
		} else if (StringUtils.equals(entity.getFacebookId(), user.getId())) {
			entity.setFacebookToken(fbAccessToken);
			entity.setUpdatedDateTime(clock.cal());
			sessionFactory.getCurrentSession().update(entity);
		}
		mapper.map(entity, userDto);
		return userDto;
	}
	
	/**
	 * This method is used to save registrationId for a user
	 * @param userId
	 * @param registrationId
	 */
	public void saveRegistrationIdForUser(Integer userId, String registrationId) {
		Session session = sessionFactory.getCurrentSession();
		UserEntity user = (UserEntity) session.load(UserEntity.class, userId);
		user.setUpdatedDateTime(clock.cal());
		user.setRegistrationId(registrationId);
		session.update(user);
	}
	
	/**
	 * Method for forming SQL query for discovering new users
	 * @return
	 */
    private String discoverNewUsersQuery() {
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT * FROM Socyal.USER ");
    	query.append("where FIRST_NAME LIKE :search_string OR LAST_NAME LIKE :search_string ");
    	query.append("AND ID NOT IN ");
    	query.append("(SELECT USER_ID FROM Socyal.USER_FOLLOWER_MAPPING WHERE FOLLOWER_USER_ID = :current_user_id) ");
    	return query.toString();
    }
}
