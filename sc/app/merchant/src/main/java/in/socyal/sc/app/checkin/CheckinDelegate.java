package in.socyal.sc.app.checkin;

import java.util.List;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface CheckinDelegate {
	public List<CheckinResponseDto> getRestaurantCheckins(Integer restaurantId, Integer page);
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException;
}
