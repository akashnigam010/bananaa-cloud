package in.socyal.sc.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.cache.CityCache;
import in.socyal.sc.cache.LocalityCache;

@Component
public class LocalityCookieHelper {
	@Autowired
	CityCache cityCache;
	@Autowired
	LocalityCache localityCache;

	public LocalityCookieDto getLocalityData(String locationCookie) throws BusinessException {
		LocalityDto locality = localityCache.getLocality(locationCookie);
		LocalityCookieDto dto = null;
		if (locality != null) {
			dto = new LocalityCookieDto(false, locality.getNameId(), locality.getName());
		} else {
			CityDto city = cityCache.getCity(locationCookie);
			if (city == null) {
				// by default - city must be selected in cache
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
			dto = new LocalityCookieDto(true, city.getNameId(), city.getName());
		}
		return dto;
	}

}
