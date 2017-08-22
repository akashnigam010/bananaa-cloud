package in.socyal.sc.persistence;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.UserDaoMapper;

@Repository
public class UserDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	UserDaoMapper mapper;

	public UserDao() {
	}

	public UserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserDto getUserByNameId(String nameId) {
		UserDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("nameId", nameId));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new UserDto();
			mapper.map(entity, dto, true, false);
		}
		return dto;
	}

	public UserDto getUserByUid(String uid) {
		UserDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("uid", uid));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new UserDto();
			mapper.map(entity, dto, false, false);
		}
		return dto;
	}
	
	public UserDto getUserByEmail(String email) {
		UserDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("email", email));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new UserDto();
			mapper.map(entity, dto, false, false);
		}
		return dto;
	}
	
	public UserDto getUserByEmailWithPassword(String email) {
		UserDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("email", email));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new UserDto();
			mapper.map(entity, dto, false, true);
		}
		return dto;
	}

	public UserDto saveUser(UserDto user) {
		UserEntity entity = mapper.map(user);
		Integer id = (Integer) sessionFactory.getCurrentSession().save(entity);
		user.setId(id);
		return user;
	}
}
