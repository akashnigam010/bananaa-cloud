package in.socyal.sc.persistence.mapper;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.UserEntity;

@Component
public class UserDaoMapper {

	public void map(UserEntity from, UserDto to) {
		to.setEmail(from.getEmail());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setId(from.getId());
		to.setImageUrl(from.getImageUrl());
	}

}
