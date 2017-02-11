package in.socyal.sc.app.checkin;

import in.socyal.sc.api.checkin.business.request.BusinessApproveCheckinRequest;
import in.socyal.sc.api.checkin.business.request.BusinessCancelCheckinRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinDetailsRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinHistoryRequest;
import in.socyal.sc.api.checkin.business.request.GetBusinessCheckinsRequest;
import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinHistoryResponse;
import in.socyal.sc.api.checkin.business.response.GetBusinessCheckinsResponse;
import in.socyal.sc.api.checkin.request.AroundMeFeedsRequest;
import in.socyal.sc.api.checkin.request.CancelCheckinRequest;
import in.socyal.sc.api.checkin.request.CheckinRequest;
import in.socyal.sc.api.checkin.request.ConfirmCheckinRequest;
import in.socyal.sc.api.checkin.request.GetMerchantCheckinsRequest;
import in.socyal.sc.api.checkin.request.LikeCheckinRequest;
import in.socyal.sc.api.checkin.request.MyFeedsRequest;
import in.socyal.sc.api.checkin.request.ProfileFeedsRequest;
import in.socyal.sc.api.checkin.request.ValidateCheckinRequest;
import in.socyal.sc.api.checkin.response.CancelCheckinResponse;
import in.socyal.sc.api.checkin.response.ConfirmCheckinResponse;
import in.socyal.sc.api.checkin.response.FeedsResponse;
import in.socyal.sc.api.checkin.response.GetCheckinStatusResponse;
import in.socyal.sc.api.checkin.response.LikeCheckinResponse;
import in.socyal.sc.api.checkin.response.ValidateCheckinResponse;
import in.socyal.sc.helper.exception.BusinessException;

public interface CheckinDelegate {
	public FeedsResponse getMerchantCheckins(GetMerchantCheckinsRequest request);
	public FeedsResponse getMyFeeds(MyFeedsRequest request);
	public FeedsResponse getProfileFeeds(ProfileFeedsRequest request);
	public FeedsResponse getAroundMeFeeds(AroundMeFeedsRequest request);
	public ConfirmCheckinResponse confirmCheckin(ConfirmCheckinRequest request) throws BusinessException;
	public ValidateCheckinResponse validateCheckin(ValidateCheckinRequest request) throws BusinessException;
	public CancelCheckinResponse cancelCheckin(CancelCheckinRequest request) throws BusinessException;
	public GetCheckinStatusResponse getCheckinStatus(CheckinRequest request) throws BusinessException;
	public LikeCheckinResponse likeACheckin(LikeCheckinRequest request) throws BusinessException;
	public LikeCheckinResponse unLikeACheckin(LikeCheckinRequest request) throws BusinessException;
	public GetBusinessCheckinsResponse getBusinessCheckins(GetBusinessCheckinsRequest request);
	public BusinessCheckinDetailsResponse getBusinessCheckinDetails(GetBusinessCheckinDetailsRequest request) throws BusinessException;
	public GetBusinessCheckinHistoryResponse getBusinessCheckinHistory(GetBusinessCheckinHistoryRequest request) throws BusinessException;
	public BusinessCheckinDetailsResponse businessCancelCheckin(BusinessCancelCheckinRequest request) throws BusinessException;
	public BusinessCheckinDetailsResponse businessApproveCheckin(BusinessApproveCheckinRequest request) throws BusinessException;
}
