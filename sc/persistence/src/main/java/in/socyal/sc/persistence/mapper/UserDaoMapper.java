package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class UserDaoMapper {
	@Autowired
	Clock clock;

	public void map(UserEntity from, UserDto to) {
		to.setEmail(from.getEmail());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setId(from.getId());
		to.setImageUrl(from.getImageUrl());
	}

	public void map(List<UserEntity> users, List<UserDto> userDtos) {
		for (UserEntity user : users) {
			UserDto userDto = new UserDto();
			map(user, userDto);
			userDtos.add(userDto);
		}
	}
}
