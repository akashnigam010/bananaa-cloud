package in.socyal.sc.persistence;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.error.LoginErrorCodeType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.user.request.SavePreferencesRequest;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.entity.VegnonvegEntity;
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

	public void getUserPreferences(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		for (SuggestionEntity s : entity.getSuggestionPreferences()) {
			System.out.println(s.getName());
		}
		for (CuisineEntity s : entity.getCuisinePreferences()) {
			System.out.println(s.getName());
		}
		System.out.println("Hurray" + entity.getFirstName());
	}

	@SuppressWarnings("unchecked")
	public void saveUserPreferences(SavePreferencesRequest request, Integer userId) throws BusinessException {
		boolean isPreferenceChanged = false;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity user = (UserEntity) criteria.uniqueResult();
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		if (request.getVegnonvegId() != null) {
			if (user.getVegnonvegPreference() == null
					|| !user.getVegnonvegPreference().getId().equals(request.getVegnonvegId())) {
				Criteria vnvCriteria = sessionFactory.getCurrentSession().createCriteria(VegnonvegEntity.class);
				vnvCriteria.add(Restrictions.eq("id", request.getVegnonvegId()));
				VegnonvegEntity vnvEntity = (VegnonvegEntity) vnvCriteria.uniqueResult();
				if (vnvEntity != null) {
					user.setVegnonvegPreference(vnvEntity);
					isPreferenceChanged = true;
				}
			}
		}

		if (request.getCuisinePreferences() != null) {
			Criteria cuisineCriteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
			cuisineCriteria.add(Restrictions.in("id", request.getCuisinePreferences()));
			List<CuisineEntity> cuisines = cuisineCriteria.list();
			user.setCuisinePreferences(cuisines);
			isPreferenceChanged = true;
		}

		if (request.getSuggestionPreferences() != null) {
			Criteria suggestionCriteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
			suggestionCriteria.add(Restrictions.in("id", request.getSuggestionPreferences()));
			List<SuggestionEntity> suggestions = suggestionCriteria.list();
			user.setSuggestionPreferences(suggestions);
			isPreferenceChanged = true;
		}

		if (isPreferenceChanged) {
			user.setUpdatedDateTime(Calendar.getInstance());
			sessionFactory.getCurrentSession().save(user);
		}
	}
}
