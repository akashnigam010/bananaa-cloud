package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.persistence.entity.LocalityEntity;

@Component
public class LocationDaoMapper {

	public void map(List<LocalityEntity> localities, List<LocalityDto> localityDtos) {
		LocalityDto dto = null;
		for (LocalityEntity entity : localities) {
			dto = new LocalityDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setCity(entity.getCity());
			dto.setLatitude(entity.getLatitude());
			dto.setLongitude(entity.getLongitude());
			localityDtos.add(dto);
		}
	}

}
