package in.socyal.sc.cache;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.cuisine.dto.CuisineDto;

@Component
public class CuisineCache {
	// TODO: add caching logic
	public CuisineDto getCuisine(String nameId) {
		if (nameId.equalsIgnoreCase("italian")) {
			CuisineDto cuisine = new CuisineDto();
			cuisine.setId(1);
			cuisine.setName("Italian");
			cuisine.setNameId("italian");
			return cuisine;
		} else if (nameId.equalsIgnoreCase("mexican")) {
			CuisineDto cuisine = new CuisineDto();
			cuisine.setId(2);
			cuisine.setName("Mexican");
			cuisine.setNameId("mexican");
			return cuisine;
		}
		return null;
	}
}
