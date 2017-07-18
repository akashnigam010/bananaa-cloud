package in.socyal.sc.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.cache.CityCache;
import in.socyal.sc.cache.LocalityCache;

@Component
public class LocalityCookieHelper {
	@Autowired
	CityCache cityCache;
	@Autowired
	LocalityCache localityCache;

	public LocationCookieDto getLocalityData(String locationCookie) throws BusinessException {
		LocalityDto locality = localityCache.getLocality(locationCookie);
		LocationCookieDto dto = null;
		if (locality != null) {
			dto = new LocationCookieDto(false, locality.getCity().getNameId(), locality.getNameId(), locality.getName());
		} else {
			CityDto city = cityCache.getCity(locationCookie);
			if (city == null) {
				//TODO: by default - city must be selected in cache - fix for future releases
				city = cityCache.getCity("hyderabad");
			}
			dto = new LocationCookieDto(true, city.getNameId(), null, city.getName());
		}
		return dto;
	}
	
	public CityDto addDefaultCityCookie(HttpServletResponse httpResponse) {
		// TODO: fix default city - change in Home Controller too
		CityDto city = cityCache.getCity("hyderabad");
		Cookie localityCookie = new Cookie("loc", city.getNameId());
		localityCookie.setPath("/");
		httpResponse.addCookie(localityCookie);
		return city;
	}

}
