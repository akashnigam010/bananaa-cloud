package in.socyal.sc.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.socyal.sc.api.checkin.dto.CheckinResponseDto;
import in.socyal.sc.api.checkin.request.AroundMeFeedsRequest;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.MyFeedsRequest;
import in.socyal.sc.api.checkin.request.ProfileFeedsRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.CancelCheckinResponse;
import in.socyal.sc.api.checkin.response.FeedsResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.GetCheckinStatusResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.app.checkin.CheckinDelegate;
import in.socyal.sc.core.mapper.CheckinServiceMapper;
import in.socyal.sc.core.validation.CheckinValidator;
import in.socyal.sc.helper.ResponseHelper;
import in.socyal.sc.helper.exception.BusinessException;

@RestController
@RequestMapping(value = "/checkin")
public class CheckinService {
	@Autowired CheckinDelegate delegate;
	@Autowired CheckinServiceMapper mapper;
	@Autowired ResponseHelper responseHelper;
	@Autowired CheckinValidator validator;

	@RequestMapping(value = "/getMerchantCheckins", method = RequestMethod.POST, headers = "Accept=application/json")
	public FeedsResponse getMerchantCheckins(@RequestBody CheckinRequest request) {
		FeedsResponse response = new FeedsResponse();
		List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(request.getId(), request.getPage());
		mapper.map(checkins, response, request.getPage());
		return responseHelper.success(response);
	}
	
	@RequestMapping(value = "/confirmCheckin", method = RequestMethod.POST, headers = "Accept=application/json")
	public ConfirmCheckinResponse checkin(@RequestBody ConfirmCheckinRequest request) {
		ConfirmCheckinResponse response = new ConfirmCheckinResponse();
		try {
			validator.validateConfirmCheckinRequest(request);
			response = delegate.confirmCheckin(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/validateCheckin", method = RequestMethod.POST, headers = "Accept=application/json")
	public ValidateCheckinResponse validateCheckin(@RequestBody ValidateCheckinRequest request) {
		ValidateCheckinResponse response = new ValidateCheckinResponse();
		try {
			validator.validateValidateCheckinRequest(request);
			response = delegate.validateCheckin(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/cancelCheckin", method = RequestMethod.POST, headers = "Accept=application/json")
	public CancelCheckinResponse cancelCheckin(@RequestBody CancelCheckinRequest request) {
		CancelCheckinResponse response = new CancelCheckinResponse();
		try {
			validator.validateCancelCheckinRequest(request);
			response = delegate.cancelCheckin(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getAroundMeFeeds", method = RequestMethod.POST, headers = "Accept=application/json")
	public FeedsResponse getAroundMeCheckins(@RequestBody AroundMeFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		try {
			validator.validateAroundMeFeedsRequest(request);
			List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(123, request.getPage());
			mapper.map(checkins, response, request.getPage());
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}		
	}
	
	@RequestMapping(value = "/getMyFeeds", method = RequestMethod.POST, headers = "Accept=application/json")
	public FeedsResponse getMyCheckins(@RequestBody MyFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		try {
			validator.validateMyFeedsRequest(request);
			List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(123, request.getPage());
			mapper.map(checkins, response, request.getPage());
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getProfileFeeds", method = RequestMethod.POST, headers = "Accept=application/json")
	public FeedsResponse getProfileFeeds(@RequestBody ProfileFeedsRequest request) {
		FeedsResponse response = new FeedsResponse();
		try {
			validator.validateProfileFeedsRequest(request);
			List<CheckinResponseDto> checkins = delegate.getRestaurantCheckins(123, request.getPage());
			mapper.map(checkins, response, request.getPage());
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/getCheckinStatus", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetCheckinStatusResponse getCheckinStatus(@RequestBody CheckinRequest request) {
		GetCheckinStatusResponse response = new GetCheckinStatusResponse();
		try {
			validator.validateCheckinRequest(request);
			System.out.println("get checkin status for ID : " + request.getId());
			response = delegate.getCheckinStatus(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}
