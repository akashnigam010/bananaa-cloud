package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.LoginErrorCodeType;
import in.socyal.sc.persistence.cache.BnaCacheManager;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.entity.UserEntity;
import in.socyal.sc.persistence.mapper.DishDaoMapper;
import in.socyal.sc.persistence.mapper.LocationDaoMapper;

@Repository
public class CacheDao {
	@Autowired
	BnaCacheManager cacheManager;
	@Autowired
	DishDaoMapper dishMapper;
	@Autowired
	LocationDaoMapper locationMapper;
	@Autowired
	SessionFactory sessionFactory;

	public CacheDao() {}

	public List<GlobalSearchItem> searchTagsWithUserPrefs(String searchString, Integer page, Integer resultsPerPage,
			TagType tagType, Integer userId) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("id", userId));
		UserEntity user = (UserEntity) criteria.uniqueResult();
		if (user == null) {
			throw new BusinessException(LoginErrorCodeType.USER_NOT_FOUND);
		}
		if (tagType == TagType.CUISINE) {
			return dishMapper.mapTagsWithPreferences(cacheManager.getCuisines(page, resultsPerPage, searchString),
					filterUserPreferencesOnSearchStringCuisines(user, searchString));
		} else {
			return dishMapper.mapTagsWithPreferences(cacheManager.getSuggestions(page, resultsPerPage, searchString),
					filterUserPreferencesOnSearchStringSuggestions(user, searchString));
		}
	}
	
	public List<LocalityDto> getAllLocalities() {
		List<LocalityEntity> entities = cacheManager.getLocalities();
		return locationMapper.map(entities);
	}
	
	private List<CuisineEntity> filterUserPreferencesOnSearchStringCuisines(UserEntity user, String searchString) {
		if (StringUtils.isNotBlank(searchString)) {
			List<CuisineEntity> cuisines = new ArrayList<>();
			for (CuisineEntity entity : user.getCuisinePreferences()) {
				if (entity.getName().toLowerCase().contains(searchString.toLowerCase())) {
					cuisines.add(entity);
				}
			}
			return cuisines;
		} else {
			return user.getCuisinePreferences();
		}
	}
	
	private List<SuggestionEntity> filterUserPreferencesOnSearchStringSuggestions(UserEntity user, String searchString) {
		if (StringUtils.isNotBlank(searchString)) {
			List<SuggestionEntity> suggestions = new ArrayList<>();
			for (SuggestionEntity entity : user.getSuggestionPreferences()) {
				if (entity.getName().toLowerCase().contains(searchString.toLowerCase())) {
					suggestions.add(entity);
				}
			}
			return suggestions;
		} else {
			return user.getSuggestionPreferences();
		}
	}
}
