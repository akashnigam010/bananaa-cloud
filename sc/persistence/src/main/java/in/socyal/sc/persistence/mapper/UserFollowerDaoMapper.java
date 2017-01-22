package in.socyal.sc.persistence.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.UserFollowerMappingEntity;

@Component
public class UserFollowerDaoMapper {
	@Autowired Clock clock;
	@Autowired UserDaoMapper userDaoMapper;

	public void map(List<UserFollowerMappingEntity> users, List<UserDto> userDtos) {
		for (UserFollowerMappingEntity user : users) {
			UserDto userDto = new UserDto();
			userDaoMapper.map(user.getUser(), userDto);
			userDtos.add(userDto);
		}
	}
}
