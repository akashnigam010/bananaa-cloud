package in.socyal.sc.user.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.login.dto.LoginUserDto;
import in.socyal.sc.api.user.dto.UserDto;

@Component
public class UserMapper {
	public LoginUserDto map(UserDto from, Integer userCheckinCount) {
		LoginUserDto user = new LoginUserDto();
		user.setId(from.getId());
		user.setFirstName(from.getFirstName());
		user.setLastName(from.getLastName());
		user.setImageUrl(from.getImageUrl());
		user.setUserCheckins(userCheckinCount);
		return user;
	}

	public List<in.socyal.sc.api.login.dto.LoginUserDto> map(List<UserDto> users) {
		List<in.socyal.sc.api.login.dto.LoginUserDto> friends = new ArrayList<>();
		if (users == null) {
			return friends;
		}
		in.socyal.sc.api.login.dto.LoginUserDto user = null;
		for (UserDto dto : users) {
			user = new in.socyal.sc.api.login.dto.LoginUserDto();
			user.setId(dto.getId());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setImageUrl(dto.getImageUrl());
			//FIXME : add lazy loading for user approved checkin count
			user.setUserCheckins(10);
			friends.add(user);
		}
		return friends;
	}
}
