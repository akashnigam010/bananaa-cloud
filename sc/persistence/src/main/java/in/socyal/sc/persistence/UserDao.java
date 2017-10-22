package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.LoginErrorCodeType;
import in.socyal.sc.api.user.dto.Profile;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.entity.UserStatusEntity;
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

	public void saveVegnonvegPreference(Integer vegnonvegId, Integer userId) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity user = (UserEntity) criteria.uniqueResult();
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		if (user.getVegnonvegPreference() == null || !user.getVegnonvegPreference().getId().equals(vegnonvegId)) {
			Criteria vnvCriteria = sessionFactory.getCurrentSession().createCriteria(VegnonvegEntity.class);
			vnvCriteria.add(Restrictions.eq("id", vegnonvegId));
			VegnonvegEntity vnvEntity = (VegnonvegEntity) vnvCriteria.uniqueResult();
			if (vnvEntity != null) {
				user.setVegnonvegPreference(vnvEntity);
				user.setUpdatedDateTime(Calendar.getInstance());
				sessionFactory.getCurrentSession().save(user);
			}
		}
	}

	public void updateTagPreference(Integer id, Integer userId, TagType tagType, boolean isRemove)
			throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity user = (UserEntity) criteria.uniqueResult();
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		if (tagType == TagType.CUISINE) {
			List<CuisineEntity> cuisinePrefs = user.getCuisinePreferences();
			if (isRemove) {
				List<CuisineEntity> newPrefs = new ArrayList<>();
				for (CuisineEntity cuisine : cuisinePrefs) {
					if (!cuisine.getId().equals(id)) {
						newPrefs.add(cuisine);
					}
				}
				user.setUpdatedDateTime(Calendar.getInstance());
				user.setCuisinePreferences(newPrefs);
			} else {
				if (cuisinePrefs == null) {
					cuisinePrefs = new ArrayList<>();
				}
				boolean canUpdate = true;
				Criteria cuisineCriteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
				cuisineCriteria.add(Restrictions.eq("id", id));
				CuisineEntity newCuisine = (CuisineEntity) cuisineCriteria.uniqueResult();
				for (CuisineEntity s : cuisinePrefs) {
					if (s.getId().equals(newCuisine.getId())) {
						canUpdate = false;
						break;
					}
				}
				if (canUpdate) {
					cuisinePrefs.add(newCuisine);
					user.setUpdatedDateTime(Calendar.getInstance());
					user.setCuisinePreferences(cuisinePrefs);
				}
			}
		} else if (tagType == TagType.SUGGESTION) {
			List<SuggestionEntity> suggestionPrefs = user.getSuggestionPreferences();
			if (isRemove) {
				List<SuggestionEntity> newPrefs = new ArrayList<>();
				for (SuggestionEntity suggestion : suggestionPrefs) {
					if (!suggestion.getId().equals(id)) {
						newPrefs.add(suggestion);
					}
				}
				user.setUpdatedDateTime(Calendar.getInstance());
				user.setSuggestionPreferences(newPrefs);
			} else {
				if (suggestionPrefs == null) {
					suggestionPrefs = new ArrayList<>();
				}
				boolean canUpdate = true;
				Criteria suggestionCriteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
				suggestionCriteria.add(Restrictions.eq("id", id));
				SuggestionEntity newSuggestion = (SuggestionEntity) suggestionCriteria.uniqueResult();
				for (SuggestionEntity s : suggestionPrefs) {
					if (s.getId().equals(newSuggestion.getId())) {
						canUpdate = false;
						break;
					}
				}
				if (canUpdate) {
					suggestionPrefs.add(newSuggestion);
					user.setUpdatedDateTime(Calendar.getInstance());
					user.setSuggestionPreferences(suggestionPrefs);
				}				
			}
		}
	}

	public Integer getVegnonvegPreference(Integer userId) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity user = (UserEntity) criteria.uniqueResult();
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		VegnonvegEntity entity = user.getVegnonvegPreference();
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}
	
	public Profile getUserProfile(Integer id) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", id));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		if (entity == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		return mapper.map(entity);
	}
	
	public void saveStatus(String status, Integer userId) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity entity = (UserEntity) criteria.uniqueResult();
		if (entity == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		UserStatusEntity statusEntity = entity.getStatus();
		if (statusEntity == null) {
			statusEntity = new UserStatusEntity();
		}
		statusEntity.setStatus(status);
		entity.setStatus(statusEntity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}
}
