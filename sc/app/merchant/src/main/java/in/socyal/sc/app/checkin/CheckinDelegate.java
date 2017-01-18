package in.socyal.sc.app.checkin;

import java.util.List;

import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.request.AroundMeFeedsRequest;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.LikeCheckinRequest;
import in.socyal.sc.api.checkin.request.MyFeedsRequest;
import in.socyal.sc.api.checkin.request.ProfileFeedsRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.CancelCheckinResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.GetCheckinStatusResponse;
import in.socyal.sc.api.checkin.response.LikeCheckinResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface CheckinDelegate {
	public List<CheckinDto> getMerchantCheckins(Integer restaurantId, Integer page);
	public List<CheckinDto> getMyFeeds(MyFeedsRequest request);
	public List<CheckinDto> getProfileFeeds(ProfileFeedsRequest request);
	public List<CheckinDto> getAroundMeFeeds(AroundMeFeedsRequest request);
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException;
	public ValidateCheckinResponse validateCheckin(ValidateCheckinRequest request) throws BusinessException;
	public CancelCheckinResponse cancelCheckin(CancelCheckinRequest request) throws BusinessException;
	public GetCheckinStatusResponse getCheckinStatus(CheckinRequest request) throws BusinessException;
	public LikeCheckinResponse likeACheckin(LikeCheckinRequest request) throws BusinessException;
}
