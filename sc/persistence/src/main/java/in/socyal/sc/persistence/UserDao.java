package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restfb.types.User;

import in.socyal.sc.api.user.dto.UserDto;
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

	public List<UserDto> fetchUsersBySearchString(String searchString) {
		List<UserDto> userDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		Criterion firstNameCriteria = Restrictions.ilike(FIRST_NAME, searchString, MatchMode.ANYWHERE);
		Criterion lastNameCriteria = Restrictions.ilike(LAST_NAME, searchString, MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(firstNameCriteria, lastNameCriteria));
		@SuppressWarnings("unchecked")
		List<UserEntity> users = (List<UserEntity>) criteria.list();
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
			sessionFactory.getCurrentSession().save(entity);
		}
		mapper.map(entity, userDto);
		return userDto;
	}
}
