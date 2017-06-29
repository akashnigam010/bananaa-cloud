package in.socyal.sc.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.persistence.LocationDao;

@Component
public class LocalityCache {
	@Autowired
	LocationDao locationDao;

	// TODO: add caching logic on server startup
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public LocalityDto getLocality(String nameId) {
		Map<String, LocalityDto> locationMap = locationDao.getLocalityCache();
		return locationMap.get(nameId);
	}
}
