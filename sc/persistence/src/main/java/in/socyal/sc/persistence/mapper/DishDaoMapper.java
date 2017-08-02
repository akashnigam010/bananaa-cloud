package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.dish.dto.DishFilterCriteria;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.PopularTag;
import in.socyal.sc.api.item.response.Tag;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.api.type.SearchType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.TimestampHelper;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.MerchantCuisineRatingEntity;
import in.socyal.sc.persistence.entity.MerchantRatingEntity;
import in.socyal.sc.persistence.entity.MerchantSuggestionRatingEntity;
import in.socyal.sc.persistence.entity.PopularTagEntity;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.entity.TagEntity;

@Component
public class DishDaoMapper {
	@Autowired
	MerchantDaoMapper merchantMapper;
	@Autowired
	UserDaoMapper userMapper;
	@Autowired
	TimestampHelper timestampHelper;

	public DishDto map(DishEntity entity, MerchantFilterCriteria merchantCriteria, DishFilterCriteria dishCriteria) {
		DishDto dto = new DishDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setRating(entity.getRating());
		if (dishCriteria.getMapSuggestions()) {
			dto.setSuggestions(mapSuggestions(entity.getSuggestions()));
		}

		if (dishCriteria.getMapCuisines()) {
			dto.setCuisines(mapCuisines(entity.getCuisines()));
		}

		if (dishCriteria.getMapImages()) {
			dto.setImageUrl(entity.getImageUrl());
			dto.setThumbnail(entity.getThumbnail());
		}

		if (dishCriteria.getMapRecommendations()) {
			dto.setRecommendations(mapRecommendations(entity));
		}

		dto.setIsActive(entity.getIsActive());
		if (merchantCriteria != null) {
			MerchantDto merchant = new MerchantDto();
			merchantMapper.map(entity.getMerchant(), merchant, merchantCriteria);
			dto.setMerchant(merchant);
			dto.setItemUrl(dto.getMerchant().getNameId() + "/" + dto.getNameId());
		}
		return dto;
	}
	
	public List<GlobalSearchItem> mapTagsShortDetails(List<TagEntity> entites) {
		List<GlobalSearchItem> dtos = new ArrayList<>();
		GlobalSearchItem tag = null;
		for (TagEntity entity : entites) {
			if (entity instanceof CuisineEntity) {
				tag = new GlobalSearchItem(SearchType.CUISINE);
			} else if (entity instanceof SuggestionEntity) {
				tag = new GlobalSearchItem(SearchType.DISH);
			}			
			tag.setName(entity.getName());
			tag.setNameId(entity.getNameId());
			dtos.add(tag);
		}
		return dtos;
	}

	public List<SuggestionDto> mapSuggestions(List<SuggestionEntity> entites) {
		List<SuggestionDto> dtos = new ArrayList<>();
		for (SuggestionEntity entity : entites) {
			dtos.add(mapSuggestion(entity));
		}
		return dtos;
	}

	public SuggestionDto mapSuggestion(SuggestionEntity entity) {
		SuggestionDto dto = new SuggestionDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setThumbnail(entity.getThumbnail());
		return dto;
	}

	public List<CuisineDto> mapCuisines(List<CuisineEntity> entites) {
		List<CuisineDto> dtos = new ArrayList<>();
		for (CuisineEntity entity : entites) {
			dtos.add(mapCuisine(entity));
		}
		return dtos;
	}

	public CuisineDto mapCuisine(CuisineEntity entity) {
		CuisineDto dto = new CuisineDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setNameId(entity.getNameId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setThumbnail(entity.getThumbnail());
		return dto;
	}

	public DishDto miniMap(DishEntity entity) {
		DishDto dto = new DishDto();
		dto.setName(entity.getName());
		dto.setThumbnail(entity.getThumbnail());
		MerchantDto merchant = new MerchantDto();
		MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		merchantMapper.map(entity.getMerchant(), merchant, merchantCriteria);
		dto.setMerchant(merchant);
		dto.setItemUrl(merchant.getMerchantUrl() + "/" + entity.getNameId());
		return dto;
	}

	private List<RecommendationDto> mapRecommendations(DishEntity entity) {
		List<RecommendationDto> dtos = new ArrayList<>();
		RecommendationDto dto = null;
		for (RecommendationEntity rcmdEntity : entity.getRecommendations()) {
			dto = new RecommendationDto();
			dto.setId(rcmdEntity.getId());
			dto.setRating(rcmdEntity.getRating());
			dto.setDescription(rcmdEntity.getDescription());
			UserDto userDto = new UserDto();
			userMapper.map(rcmdEntity.getUser(), userDto, false);
			MutablePair<Integer, Integer> ratingAndReviewCount = getRatingCount(rcmdEntity.getUser().getRecommendations());
			userDto.setTotalRatings(ratingAndReviewCount.getLeft());
			userDto.setTotalReviews(ratingAndReviewCount.getRight());
			dto.setUser(userDto);
			dto.setUpdatedDateTime(rcmdEntity.getUpdatedDateTime());
			dto.setTimeDiff(timestampHelper.getTimeDiffString(rcmdEntity.getUpdatedDateTime().getTimeInMillis()));
			dtos.add(dto);
		}
		Collections.sort(dtos);
		return dtos;
	}

	public List<DishDto> map(List<DishEntity> entities, MerchantFilterCriteria merchantCriteria,
			DishFilterCriteria dishCriteria) {
		Map<Integer, DishEntity> map = new HashMap<>();
		List<DishDto> dtos = new ArrayList<>();
		for (DishEntity entity : entities) {
			if (!map.containsKey(entity.getId())) {
				map.put(entity.getId(), entity);
				dtos.add(map(entity, merchantCriteria, dishCriteria));				
			}			
		}
		return dtos;
	}

	public List<Tag> map(List<MerchantRatingEntity> entities) throws BusinessException {
		List<Tag> tags = new ArrayList<>();
		Tag tag = null;
		for (MerchantRatingEntity entity : entities) {
			tag = new Tag();
			if (entity instanceof MerchantCuisineRatingEntity) {
				tag.setId(((MerchantCuisineRatingEntity) entity).getCuisine().getId());
			} else if (entity instanceof MerchantSuggestionRatingEntity) {
				tag.setId(((MerchantSuggestionRatingEntity) entity).getSuggestion().getId());
			} else {
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
			tag.setName(entity.getTag().getName());
			tag.setRating(entity.getRating() != null ? entity.getRating().toString() : "");
			tag.setThumbnail(entity.getTag().getThumbnail());
			tag.setItemUrl("/" + entity.getTag().getNameId());
			tags.add(tag);
		}
		return tags;
	}
	
	public MutablePair<Integer, Integer> getRatingCount(List<RecommendationEntity> entities) {
		MutablePair<Integer, Integer> ratingAndReviewCount = new MutablePair<>();
		int right = 0;
		int left = entities.size();
		for (RecommendationEntity rcmd : entities) {
			if (StringUtils.isNotBlank(rcmd.getDescription())) {
				right++;
			}
		}
		ratingAndReviewCount.setRight(right);
		ratingAndReviewCount.setLeft(left);
		return ratingAndReviewCount;
	}

	public List<PopularTag> mapPopularTags(List<PopularTagEntity> entities, String preUrl) {
		List<PopularTag> tags = new ArrayList<>();
		PopularTag tag = null;
		for (PopularTagEntity entity : entities) {
			tag = new PopularTag();
			tag.setMerchants(entity.getMerchants());
			tag.setName(entity.getName());
			tag.setNameId(entity.getNameId());
			tag.setThumbnail(entity.getThumbnail());
			tag.setUrl(preUrl + entity.getNameId());
			tags.add(tag);
		}
		return tags;
	}
}
