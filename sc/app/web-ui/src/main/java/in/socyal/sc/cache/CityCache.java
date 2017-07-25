package in.socyal.sc.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.persistence.LocationDao;

@Component
public class CityCache {
	@Autowired
	LocationDao locationDao;

	// TODO: add caching logic on server startup
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public CityDto getCity(String nameId) {
		Map<String, CityDto> cityMap = locationDao.getCityCache();
		return cityMap.get(nameId);
	}
}
