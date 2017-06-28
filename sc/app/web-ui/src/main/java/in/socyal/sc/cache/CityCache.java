package in.socyal.sc.cache;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.CityDto;

@Component
public class CityCache {
	// TODO: add caching logic
	public CityDto getCity(String nameId) {
		if (nameId.equalsIgnoreCase("hyderabad")) {
			CityDto city = new CityDto();
			city.setId(1);
			city.setName("Hyderabad");
			city.setNameId("hyderabad");
			return city;
		}
		return null;
	}
}
