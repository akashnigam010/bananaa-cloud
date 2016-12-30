package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.login.dto.FacebookUser;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.UserDaoMapper;

@Repository
public class UserDao {
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	@Autowired SessionFactory sessionFactory;
	@Autowired UserDaoMapper mapper;
	
   public UserDao() {
    }
     
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public UserDto fetchUser(Integer userId) {
    	UserDto dto = null;
    	UserEntity entity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userId);
    	if (entity != null) {
    		dto = new UserDto();
    		mapper.map(entity, dto);
    	}
    	return dto;
    }
    
    @Transactional
    public List<UserDto> fetchUsers(String searchString) {
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
    
    @Transactional
    public UserDto fetchUserByFbId(String fbId) {
    	UserDto dto = null;
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
    	criteria.add(Restrictions.eq("facebookId", fbId));
    	UserEntity entity = (UserEntity) criteria.uniqueResult();
    	if (entity != null) {
    		dto = new UserDto();
    		mapper.map(entity, dto);
    	}
    	return dto;
    }
    
    @Transactional
    public UserDto saveUserDetails(FacebookUser user, String fbAccessToken) {
    	UserEntity entity = new UserEntity();
    	UserDto dto = fetchUserByFbId(user.getId());
    	if (dto == null) {
    		dto = new UserDto();
    		mapper.map(user, entity, fbAccessToken);
    		sessionFactory.getCurrentSession().save(entity);
    		mapper.map(entity, dto);
    	}
    	
    	return dto;
    }
}
