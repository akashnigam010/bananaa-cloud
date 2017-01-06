package in.socyal.sc.user.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.socyal.sc.api.login.dto.LoginUserDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.api.user.response.SearchFriendResponse;
import in.socyal.sc.api.user.response.UserProfileResponse;

@Component
public class UserMapper {
	public void map(UserDto from, UserProfileResponse to) {
		LoginUserDto user = new LoginUserDto();
		user.setId(from.getId());
		user.setFirstName(from.getFirstName());
		user.setLastName(from.getLastName());
		user.setImageUrl(from.getImageUrl());
		user.setUserCheckins(320);
		to.setUser(user);
	}

	public void map(List<UserDto> users, SearchFriendResponse response) {
		List<in.socyal.sc.api.login.dto.LoginUserDto> friends = new ArrayList<>();
		in.socyal.sc.api.login.dto.LoginUserDto user = null;
		for (UserDto dto : users) {
			user = new in.socyal.sc.api.login.dto.LoginUserDto();
			user.setId(dto.getId());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setImageUrl(dto.getImageUrl());
			friends.add(user);
		}

		response.setUsers(friends);
	}
}
