package in.socyal.sc.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.request.AroundMeCheckinsRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
<<<<<<< HEAD
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
=======
import in.socyal.sc.api.checkin.request.MyCheckinsRequest;
>>>>>>> b90b9e3d4f354e34b55e6694c2fb952ebae2d5ae
import in.socyal.sc.api.checkin.response.CheckinResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.app.checkin.CheckinDelegate;
import in.socyal.sc.core.mapper.CheckinServiceMapper;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;

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
		mapper.map(checkins, response, request.getPage());
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/confirmCheckin", method = RequestMethod.POST, headers = "Accept=application/json")
	public ConfirmCheckinResponse checkin(@RequestBody ConfirmCheckinRequest request) {
		ConfirmCheckinResponse response = new ConfirmCheckinResponse();
		try {
			response = delegate.confirmCheckin(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getAroundMeCheckins", method = RequestMethod.POST, headers = "Accept=application/json")
	public CheckinResponse getAroundMeCheckins(@RequestBody AroundMeCheckinsRequest request) {
		CheckinResponse response = new CheckinResponse();
		List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(123, request.getPage());
		mapper.map(checkins, response, request.getPage());
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/getMyCheckins", method = RequestMethod.POST, headers = "Accept=application/json")
	public CheckinResponse getMyCheckins(@RequestBody MyCheckinsRequest request) {
		CheckinResponse response = new CheckinResponse();
		List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(123, request.getPage());
		mapper.map(checkins, response, request.getPage());
		return responseHelper.success(response);
	}
}
