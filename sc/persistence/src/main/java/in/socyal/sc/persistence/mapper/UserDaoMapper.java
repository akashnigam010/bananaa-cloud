package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.Profile;
import in.socyal.sc.api.user.dto.Tag;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.TimestampHelper;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.TagEntity;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class UserDaoMapper {
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String VEG_PREF = "user.pref.veg";
	private static final String NONVEG_PREF = "user.pref.nonveg";
	private static final String ANY_PREF = "user.pref.anything";
	@Autowired
	DishDaoMapper dishMapper;
	@Autowired
	TimestampHelper timestampHelper;

	public UserEntity map(UserDto from) {
		Calendar cal = Calendar.getInstance();
		UserEntity to = new UserEntity(cal, cal);
		to.setUid(from.getUid());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setNameId(from.getNameId());
		to.setEmail(from.getEmail());
		to.setImageUrl(from.getImageUrl());
		to.setCredibility(from.getCredibility());
		to.setPassword(from.getPassword());
		return to;
	}

	public void map(UserEntity from, UserDto to, Boolean mapRecommendations, Boolean mapPassword) {
		to.setId(from.getId());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setImageUrl(from.getImageUrl());
		to.setUserUrl("/user/" + from.getNameId());
		to.setNameId(from.getNameId());
		to.setCredibility(from.getCredibility());
		if (mapPassword) {
			to.setPassword(from.getPassword());
		}
		MutablePair<Integer, Integer> ratingAndReviewCount = dishMapper.getRatingCount(from.getRecommendations());
		to.setTotalRatings(ratingAndReviewCount.getLeft());
		to.setTotalReviews(ratingAndReviewCount.getRight());
		if (mapRecommendations) {
			List<RecommendationDto> dtos = new ArrayList<>();
			RecommendationDto dto = null;
			for (RecommendationEntity entity : from.getRecommendations()) {
				dto = new RecommendationDto();
				dto.setId(entity.getId());
				dto.setRating(entity.getRating());
				dto.setUpdatedDateTime(entity.getUpdatedDateTime());
				dto.setTimeDiff(timestampHelper.getTimeDiffString(entity.getUpdatedDateTime().getTimeInMillis()));
				dto.setDescription(entity.getDescription());
				dto.setDish(dishMapper.miniMap(entity.getDish()));
				dtos.add(dto);
			}
			Collections.sort(dtos);
			to.setRecommendations(dtos);
		}
	}

	public Profile map(UserEntity entity) {
		Profile profile = new Profile();
		profile.setId(entity.getId());
		profile.setName(entity.getFirstName() + " " + entity.getLastName());
		profile.setImageUrl(entity.getImageUrl());
		profile.setLevel(entity.getCredibility());
		MutablePair<Integer, Integer> ratingAndReviewCount = dishMapper.getRatingCount(entity.getRecommendations());
		profile.setRatingCount(ratingAndReviewCount.getLeft());
		profile.setFoodviewCount(ratingAndReviewCount.getRight());
		profile.setCuisines(mapTagsForProfile(entity.getCuisinePreferences()));
		profile.setDishes(mapTagsForProfile(entity.getSuggestionPreferences()));
		if (entity.getStatus() != null) {
			profile.setStatus(entity.getStatus().getStatus());
		} else {
			if (entity.getVegnonvegPreference() != null) {
				switch (entity.getVegnonvegPreference().getId()) {
					case 1: profile.setStatus(resource.getString(VEG_PREF)); break;
					case 2: profile.setStatus(resource.getString(NONVEG_PREF)); break;
					case 3: profile.setStatus(resource.getString(ANY_PREF)); break;
				}
			}
		}
		return profile;
	}

	private List<Tag> mapTagsForProfile(List<? extends TagEntity> tags) {
		List<Tag> profileTags = new ArrayList<>();
		Tag profileTag = null;
		for (TagEntity tag : tags) {
			profileTag = new Tag(tag.getId(), tag.getName());
			profileTags.add(profileTag);
		}
		return profileTags;
	}
}
