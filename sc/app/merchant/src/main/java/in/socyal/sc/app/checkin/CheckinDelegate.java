package in.socyal.sc.app.checkin;

import java.util.List;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;

public interface CheckinDelegate {
	public List<CheckinResponseDto> getRestaurantCheckins(Integer restaurantId, Integer page);
}
