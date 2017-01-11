package in.socyal.sc.persistence.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.socyal.sc.api.checkin.dto.CheckinUserLikeDto;
import in.socyal.sc.api.user.dto.UserDto;
import in.socyal.sc.persistence.entity.CheckinUserLikeEntity;

@Component
public class CheckinUserLikeMapper {
	@Autowired UserDaoMapper userMapper;
	
	public void map(CheckinUserLikeEntity from, CheckinUserLikeDto to) {
		to.setCheckinId(from.getCheckinId());
		to.setId(from.getId());
		UserDto user = new UserDto();
		userMapper.map(from.getUser(), user);
		to.setUser(user);
	}
}
