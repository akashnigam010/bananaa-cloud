package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.helper.NumberUtils;
import in.socyal.sc.persistence.entity.AddressEntity;
import in.socyal.sc.persistence.entity.ContactEntity;
import in.socyal.sc.persistence.entity.MerchantCuisineRatingEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.MerchantRatingEntity;
import in.socyal.sc.persistence.entity.MerchantSuggestionRatingEntity;
import in.socyal.sc.persistence.entity.TimingEntity;
import in.socyal.sc.persistence.entity.TrendingMerchantResultEntity;

@Component
public class MerchantDaoMapper {
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String MINIMUM_TAG_RATING_DISPLAY = "minimum.rating.display";
	
	@Autowired
	LocationDaoMapper mapper;
	@Autowired
	NumberUtils numberUtils;
	
	public void map(Collection<MerchantEntity> from, List<MerchantDto> to, MerchantFilterCriteria filter) {
		// using map to remove duplicates
		Map<Integer, MerchantEntity> merchantMap = new HashMap<>();
		for (MerchantEntity entity : from) {
			if (!merchantMap.containsKey(entity.getId())) {
				merchantMap.put(entity.getId(), entity);
				MerchantDto dto = new MerchantDto();
				map(entity, dto, filter);
				to.add(dto);
			}			
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
		dto.setMerchantUrl("/" + entity.getAddress().getLocality().getCity().getNameId() + "/" + entity.getNameId());
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
			int lastIndex = entity.getCuisineRatings().size() >= 4 ? 4 : entity.getCuisineRatings().size();
			for (MerchantCuisineRatingEntity tagEntity : entity.getCuisineRatings().subList(0, lastIndex)) {
				if (tagEntity.getRating() < Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_DISPLAY))) {
					// break the loop, do not map rest of the cuisines if rating
					// drops below minimum required rating
					break;
				}
				tag = new Tag();
				tag.setId(tagEntity.getCuisine().getId());
				tag.setName(tagEntity.getCuisine().getName());
				tag.setNameId(tagEntity.getCuisine().getNameId());
				tag.setDishCount(tagEntity.getDishCount());
				tag.setRating(tagEntity.getRating().toString());
				tag.setItemUrl("/" + entity.getAddress().getLocality().getCity().getNameId() + "/"
						+ tagEntity.getCuisine().getNameId());
				tag.setThumbnail(tagEntity.getCuisine().getThumbnail());
				dto.getRatedCuisines().add(tag);
			}
			Collections.sort(dto.getRatedCuisines());
		}
		
		if (filter.getMapSuggestionRatings()) {
			Tag tag = null;
			int lastIndex = entity.getSuggestionRatings().size() >= 4 ? 4 : entity.getSuggestionRatings().size();
			for (MerchantSuggestionRatingEntity tagEntity : entity.getSuggestionRatings().subList(0, lastIndex)) {
				if (tagEntity.getRating() < Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_DISPLAY))) {
					// break the loop, do not map rest of the suggestions if
					// rating drops below minimum required rating
					break;
				}
				tag = new Tag();
				tag.setId(tagEntity.getSuggestion().getId());
				tag.setName(tagEntity.getSuggestion().getName());
				tag.setNameId(tagEntity.getSuggestion().getNameId());
				tag.setDishCount(tagEntity.getDishCount());
				tag.setRating(tagEntity.getRating().toString());
				tag.setItemUrl("/" + entity.getAddress().getLocality().getCity().getNameId() + "/"
						+ tagEntity.getSuggestion().getNameId());
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
	
	public void map(List<TrendingMerchantResultEntity> result, List<TrendingMerchantResultDto> response,
			MerchantFilterCriteria criteria) {
		for (TrendingMerchantResultEntity entity : result) {
			TrendingMerchantResultDto dto = new TrendingMerchantResultDto();
			map(entity, dto, criteria);
			response.add(dto);
		}
	}

	public void map(TrendingMerchantResultEntity entity, TrendingMerchantResultDto dto, MerchantFilterCriteria criteria) {
		MerchantDto merchant = new MerchantDto();
		map(entity.getMerchant(), merchant, criteria);
		dto.setMerchant(merchant);
		dto.setRating(numberUtils.toFloatOneDecimal(entity.getRating()));
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
