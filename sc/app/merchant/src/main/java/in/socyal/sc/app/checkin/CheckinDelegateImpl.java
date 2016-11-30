package in.socyal.sc.app.checkin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;

@Service
public class CheckinDelegateImpl implements CheckinDelegate {
	@Override
	public List<CheckinResponseDto> getRestaurantCheckins(Integer restaurantId, Integer page) {
		List<CheckinResponseDto> checkins = new ArrayList<>();
		return checkins;
	}
}
