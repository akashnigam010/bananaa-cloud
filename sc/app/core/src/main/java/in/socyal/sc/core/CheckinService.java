package in.socyal.sc.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.response.CheckinResponse;
import in.socyal.sc.app.checkin.CheckinDelegate;
import in.socyal.sc.core.mapper.CheckinServiceMapper;
import in.socyal.sc.helper.ResponseHelper;

@RestController
@RequestMapping(value = "/checkin")
public class CheckinService {
	@Autowired CheckinDelegate delegate;
	@Autowired CheckinServiceMapper mapper;
	@Autowired ResponseHelper responseHelper;

	@RequestMapping(value = "/getMerchantCheckins", method = RequestMethod.POST, headers = "Accept=application/json")
	public CheckinResponse getMerchantCheckins(@RequestBody CheckinRequest request) {
		CheckinResponse response = new CheckinResponse();
		List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(request.getId(), request.getPage());
		mapper.map(checkins, response);
		return responseHelper.success(response);
	}
}
