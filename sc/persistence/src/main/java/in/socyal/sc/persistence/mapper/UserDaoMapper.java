package in.socyal.sc.persistence.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.recommendation.dto.RecommendationDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class UserDaoMapper {
	@Autowired
	DishDaoMapper dishMapper;

	public UserEntity map(UserDto from) {
		Calendar cal = Calendar.getInstance();
		UserEntity to = new UserEntity();
		to.setUid(from.getUid());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setNameId(from.getNameId());
		to.setEmail(from.getEmail());
		to.setImageUrl(from.getImageUrl());
		to.setCreatedDateTime(cal);
		to.setUpdatedDateTime(cal);
		return to;
	}

	public void map(UserEntity from, UserDto to, Boolean mapRecommendations) {
		to.setId(from.getId());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setImageUrl(from.getImageUrl());
		to.setUserUrl("/user/" + from.getNameId());
		if (mapRecommendations) {
			List<RecommendationDto> dtos = new ArrayList<>();
			RecommendationDto dto = null;
			for (RecommendationEntity entity : from.getRecommendations()) {
				dto = new RecommendationDto();
				dto.setId(entity.getId());
				dto.setUpdatedDateTime(entity.getUpdatedDateTime());
				dto.setDescription(entity.getDescription());
				dto.setDish(dishMapper.miniMap(entity.getDish()));
				dtos.add(dto);
			}
			to.setRecommendations(dtos);
		}
	}
}
