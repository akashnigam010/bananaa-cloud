package in.socyal.sc.persistence;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.UserDaoMapper;

@Repository
public class UserDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired UserDaoMapper mapper;
	
   public UserDao() {
    }
     
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public UserDto fetchUsers(Integer userId) {
    	UserDto dto = null;
    	UserEntity entity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userId);
    	if (entity != null) {
    		dto = new UserDto();
    		mapper.map(entity, dto);
    	}
    	return dto;
    }
}
