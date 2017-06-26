package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.merchant.dto.AddressDto;
import in.socyal.sc.api.merchant.dto.ContactDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TimingDto;
import in.socyal.sc.persistence.entity.AddressEntity;
import in.socyal.sc.persistence.entity.ContactEntity;
import in.socyal.sc.persistence.entity.MerchantCuisineRatingEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.MerchantRatingEntity;
import in.socyal.sc.persistence.entity.MerchantSuggestionRatingEntity;
import in.socyal.sc.persistence.entity.TimingEntity;

@Component
public class MerchantDaoMapper {
	private static final Float MINIMUM_TAG_RATING = 1.0f;
	@Autowired
	LocationDaoMapper mapper;
	
	public void map(List<MerchantEntity> from, List<MerchantDto> to, MerchantFilterCriteria filter) {
		for (MerchantEntity entity : from) {
			MerchantDto dto = new MerchantDto();
			map(entity, dto, filter);
			to.add(dto);
		}
	}
	
	public void mapByTag(List<MerchantRatingEntity> from, List<MerchantDto> to, MerchantFilterCriteria filter) {
		for (MerchantRatingEntity entity : from) {
			MerchantDto dto = new MerchantDto();
			map(entity.getMerchant(), dto, filter);
			to.add(dto);
		}
	}

	public void map(MerchantEntity entity, MerchantDto dto, MerchantFilterCriteria filter) {
		dto.setId(entity.getId());
		dto.setNameId(entity.getNameId());
		dto.setName(entity.getName());
		dto.setMerchantUrl(entity.getAddress().getLocality().getCity().getNameId() + "/" + entity.getNameId());
		if (filter.getMapImage()) {
			dto.setImageUrl(entity.getImageUrl());
			dto.setThumbnail(entity.getThumbnail());
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
		
		if (filter.getMapCuisineRatings()) {
			Tag tag = null;
			for (MerchantCuisineRatingEntity tagEntity : entity.getCuisineRatings()) {
				if (tagEntity.getRating() < MINIMUM_TAG_RATING) {
					// break the loop, do not map rest of the cuisines if rating
					// drops below minimum required rating
					break;
				}
				tag = new Tag();
				tag.setId(tagEntity.getId());
				tag.setName(tagEntity.getCuisine().getName());
				tag.setNameId(tagEntity.getCuisine().getNameId());
				tag.setDishCount(tagEntity.getDishCount());
				tag.setRating(tagEntity.getRating().toString());
				// TODO : set itemUrl
				// tag.setItemUrl(tagEntity.get);
				tag.setThumbnail(tagEntity.getCuisine().getThumbnail());
				dto.getRatedCuisines().add(tag);
			}
		}
		
		if (filter.getMapSuggestionRatings()) {
			Tag tag = null;
			for (MerchantSuggestionRatingEntity tagEntity : entity.getSuggestionRatings()) {
				if (tagEntity.getRating() < MINIMUM_TAG_RATING) {
					// break the loop, do not map rest of the suggestions if
					// rating drops below minimum required rating
					break;
				}
				tag = new Tag();
				tag.setId(tagEntity.getId());
				tag.setName(tagEntity.getSuggestion().getName());
				tag.setNameId(tagEntity.getSuggestion().getNameId());
				tag.setDishCount(tagEntity.getDishCount());
				tag.setRating(tagEntity.getRating().toString());
				// TODO : set itemUrl
				// tag.setItemUrl(tagEntity.get);
				tag.setThumbnail(tagEntity.getSuggestion().getThumbnail());
				dto.getRatedSuggestions().add(tag);
			}
		}
		
		if (filter.getMapOtherDetails()) {
			dto.setAverageCost(entity.getAverageCost());
			dto.setTypes(parseToList(entity.getType()));
			if (entity.getContact() != null) {
				ContactDto contactDto = new ContactDto();
				map(entity.getContact(), contactDto);
				dto.setContact(contactDto);
			}
		}
	}

	public List<TimingDto> mapTimingDtos(Set<TimingEntity> entities) {
		List<TimingDto> dtos = new ArrayList<>();
		for (TimingEntity entity : entities) {
			dtos.add(mapTimingDto(entity));
		}
		Collections.sort(dtos);
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
		to.setLocality(mapper.map(from.getLocality()));
		to.setId(from.getId());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
	}

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
