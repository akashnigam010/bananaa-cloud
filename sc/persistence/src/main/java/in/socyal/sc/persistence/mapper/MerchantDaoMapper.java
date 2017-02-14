package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.ContactDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TimingDto;
import in.socyal.sc.persistence.entity.AddressEntity;
import in.socyal.sc.persistence.entity.ContactEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.TimingEntity;

@Component
public class MerchantDaoMapper {
	public void map(List<MerchantEntity> from, List<MerchantDto> to, MerchantFilterCriteria filter) {
		for (MerchantEntity entity : from) {
			MerchantDto dto = new MerchantDto();
			map(entity, dto, filter);
			to.add(dto);
		}
	}

	public void map(MerchantEntity entity, MerchantDto dto, MerchantFilterCriteria filter) {
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		if (filter.getMapImage()) {
			dto.setImageUrl(entity.getImageUrl());
		}
		
		if (filter.getMapAddress()) {
			if (entity.getAddress() != null) {
				AddressDto addressDto = new AddressDto();
				map(entity.getAddress(), addressDto);
				dto.setAddress(addressDto);
			}
		}		
		
		if (filter.getMapTimings()) {
			dto.setTimings(mapTimingDtos(entity.getTimings()));
		}
		
		if (filter.getMapRating()) {
			dto.setRating(entity.getRating());
		}
		
		if (filter.getMapCheckins()) {
			dto.setCheckins(entity.getCheckins());
		}
		
		if (filter.getMapOtherDetails()) {
			dto.setAverageCost(entity.getAverageCost());
			dto.setCuisines(parseToList(entity.getCuisine()));
			dto.setTypes(parseToList(entity.getType()));
			if (entity.getContact() != null) {
				ContactDto contactDto = new ContactDto();
				map(entity.getContact(), contactDto);
				dto.setContact(contactDto);
			}
		}
	}

	public Set<TimingDto> mapTimingDtos(Set<TimingEntity> entities) {
		Set<TimingDto> dtos = new HashSet<>();
		for (TimingEntity entity : entities) {
			dtos.add(mapTimingDto(entity));
		}
		return dtos;
	}

	public TimingDto mapTimingDto(TimingEntity entity) {
		TimingDto dto = new TimingDto();
		dto.setId(entity.getId());
		dto.setMerchantId(entity.getMerchantId());
		dto.setDay(entity.getDay());
		dto.setOpen(entity.getOpen());
		dto.setClose(entity.getClose());
		return dto;
	}

	public Set<TimingEntity> mapTimingEntities(Set<TimingDto> dtos) {
		Set<TimingEntity> entities = new HashSet<>();
		for (TimingDto dto : dtos) {
			entities.add(mapTimingEntity(dto));
		}
		return entities;
	}

	public TimingEntity mapTimingEntity(TimingDto dto) {
		TimingEntity entity = new TimingEntity();
		entity.setId(dto.getId());
		entity.setMerchantId(dto.getMerchantId());
		entity.setDay(dto.getDay());
		entity.setOpen(dto.getOpen());
		entity.setClose(dto.getClose());
		return entity;
	}

	public void map(ContactEntity from, ContactDto to) {
		to.setEmail(from.getEmail());
		to.setId(from.getId());
		to.setPhone1(from.getPhone1());
		if (StringUtils.isNotEmpty(from.getPhone2())) {
			to.setPhone2(from.getPhone2());
		}
		if (StringUtils.isNotEmpty(from.getEmail())) {
			to.setEmail(from.getEmail());
		}
	}

	public void map(AddressEntity from, AddressDto to) {
		to.setAddress(from.getAddress());
		to.setLocality(map(from.getLocality()));
		to.setCity(from.getCity());
		to.setCountry(from.getCountry());
		to.setId(from.getId());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
		to.setState(from.getState());
		to.setZip(from.getZip());
	}

	private LocalityDto map(LocalityEntity from) {
		LocalityDto to = new LocalityDto();
		to.setId(from.getId());
		to.setCity(from.getCity());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
		to.setName(from.getName());
		return to;
	}

	// public void map(MerchantDto from, MerchantEntity to) {
	// to.setId(from.getId());
	// to.setImageUrl(from.getImageUrl());
	// to.setName(from.getName());
	// to.setTimings(mapTimingEntities(from.getTimings()));
	// to.setRating(from.getRating());
	// if (from.getAddress() != null) {
	// AddressEntity address = new AddressEntity();
	// map(from.getAddress(), address);
	// to.setAddress(address);
	// }
	//
	// if (from.getContact() != null) {
	// ContactEntity contact = new ContactEntity();
	// map(from.getContact(), contact);
	// to.setContact(contact);
	// }
	// to.setCheckins(from.getCheckins());
	// to.setCuisine(parseToList(from.getCuisines()));
	// }

	// private void map(ContactDto from, ContactEntity to) {
	// to.setEmail(from.getEmail());
	// to.setId(from.getId());
	// to.setPhone1(from.getPhone1());
	// if (StringUtils.isNotEmpty(from.getPhone2())) {
	// to.setPhone2(from.getPhone2());
	// }
	// if (StringUtils.isNotEmpty(from.getEmail())) {
	// to.setEmail(from.getEmail());
	// }
	// }
	//
	// private void map(AddressDto from, AddressEntity to) {
	// to.setAddress(from.getAddress());
	// to.setLocality(map(from.getLocality()));
	// to.setCity(from.getCity());
	// to.setCountry(from.getCountry());
	// to.setId(from.getId());
	// to.setLatitude(from.getLatitude());
	// to.setLongitude(from.getLongitude());
	// to.setState(from.getState());
	// to.setZip(from.getZip());
	// }

	// private LocalityEntity map(LocalityDto from) {
	// LocalityEntity to = new LocalityEntity();
	// to.setId(from.getId());
	// to.setCity(from.getCity());
	// to.setLatitude(from.getLatitude());
	// to.setLongitude(from.getLongitude());
	// to.setName(from.getName());
	// return to;
	// }

	/**
	 * This method parses a comma separated string splits the string on a
	 * delimiter defined as: zero or more whitespace, a literal comma, zero or
	 * more whitespace which will place the words into the list and collapse any
	 * whitespace between the words and commas
	 * 
	 * @param commaSeperatedString
	 * @return List of string
	 */
	private List<String> parseToList(String commaSeperatedString) {
		List<String> list = new ArrayList<>();
		if (commaSeperatedString != null && !commaSeperatedString.isEmpty()) {
			list = Arrays.asList(commaSeperatedString.split("\\s*,\\s*"));
		}
		return list;
	}
}
