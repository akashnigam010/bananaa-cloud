package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.persistence.entity.CityEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;

@Component
public class LocationDaoMapper {

	public void map(List<LocalityEntity> localities, List<LocalityDto> localityDtos) {
		for (LocalityEntity entity : localities) {
			localityDtos.add(map(entity));
		}
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
