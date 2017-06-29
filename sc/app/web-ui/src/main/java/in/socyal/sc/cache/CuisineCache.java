package in.socyal.sc.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.persistence.TagDao;

@Component
public class CuisineCache {
	@Autowired
	TagDao tagDao;

	// TODO: add caching logic on server startup
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public CuisineDto getCuisine(String nameId) {
		Map<String, CuisineDto> cuisineMap = tagDao.getCuisineCache();
		return cuisineMap.get(nameId);
	}
}
