package in.socyal.sc.persistence.mapper;

import java.util.Calendar;
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

	public UserEntity map(UserDto from) {
		Calendar cal = Calendar.getInstance();
		UserEntity to = new UserEntity();
		to.setUid(from.getUid());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setNameId(generateUserNameId(from, cal));
		to.setEmail(from.getEmail());
		to.setImageUrl(from.getImageUrl());
		to.setCreatedDateTime(cal);
		to.setUpdatedDateTime(cal);
		return to;
	}

	public void map(UserEntity from, UserDto to) {
		to.setId(from.getId());
		to.setUid(from.getUid());
		to.setEmail(from.getEmail());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setNameId(from.getNameId());
		to.setImageUrl(from.getImageUrl());
		to.setCreatedDateTime(from.getCreatedDateTime());
		to.setUpdatedDateTime(from.getUpdatedDateTime());
	}

	public void map(List<UserEntity> users, List<UserDto> userDtos) {
		for (UserEntity user : users) {
			UserDto userDto = new UserDto();
			map(user, userDto);
			userDtos.add(userDto);
		}
	}

	/**
	 * Generates unique nameId for user
	 * @param dto
	 * @param cal
	 * @return
	 */
	private String generateUserNameId(UserDto dto, Calendar cal) {
		String nameId = dto.getFirstName().toLowerCase().trim() + "-" + dto.getLastName().toLowerCase().trim() + "-"
				+ cal.getTimeInMillis();
		return nameId;
	}
}
