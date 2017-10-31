package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.location.dto.CityDto;
import in.socyal.sc.api.location.dto.LocalityDto;
import in.socyal.sc.persistence.entity.CityEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;

@Component
public class LocationDaoMapper {

	public List<CityDto> map(List<CityEntity> entities) {
		List<CityDto> cities = new ArrayList<>();
		CityDto city = null;
		for (CityEntity entity : entities) {
			city = new CityDto();
			city.setId(entity.getId());
			city.setName(entity.getName());
			city.setNameId(entity.getNameId());
			city.setLocalities(mapShortLocality(entity.getLocalities()));
			cities.add(city);
		}
		return cities;
	}
	
	private List<LocalityDto> mapShortLocality(List<LocalityEntity> entities) {
		List<LocalityDto> localities = new ArrayList<>();
		LocalityDto locality = null;
		for (LocalityEntity entity : entities) {
			// mapping localities which are active
			if (entity.getIsActive()) {
				locality = new LocalityDto();
				locality.setId(entity.getId());
				locality.setName(entity.getName());
				locality.setNameId(entity.getNameId());
				localities.add(locality);
			}
		}
		return localities;
	}
	
	public LocalityDto map(LocalityEntity entity) {
		LocalityDto dto = new LocalityDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setCity(map(entity.getCity()));
		dto.setLatitude(entity.getLatitude());
		dto.setLongitude(entity.getLongitude());
		return dto;
	}

	public CityDto map(CityEntity entity) {
		CityDto dto = new CityDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		return dto;
	}
}
