package in.socyal.sc.cache;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.type.LocalityType;

@Component
public class LocalityCache {
	// TODO: add caching logic
	/**
	 * TODO: create a multi-hash-map to contain city and localities. And search
	 * should always happen with cityNameId and localityNameId. Using hashing
	 * algorithm, exactly in 2 checks will be required to get a hit. If found,
	 * the pair is returned otherwise null is returned. This will bring down the
	 * search time to very low.
	 * 
	 * @param nameId
	 * @return
	 */
	public LocalityDto getLocality(String nameId) {
		LocalityType loc = LocalityType.getLocalityByNameId(nameId);
		if (loc != null) {
			LocalityDto locality = new LocalityDto();
			locality.setId(loc.getId());
			locality.setName(loc.getName());
			locality.setNameId(loc.getNameId());
			return locality;
		}
		return null;
	}
}
