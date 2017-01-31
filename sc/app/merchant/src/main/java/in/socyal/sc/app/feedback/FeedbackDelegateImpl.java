package in.socyal.sc.app.feedback;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.feedback.business.request.BusinessAskFeedbackRequest;
import in.socyal.sc.api.feedback.business.request.BusinessCancelFeedbackRequest;
import in.socyal.sc.api.feedback.business.response.BusinessAskFeedbackResponse;
import in.socyal.sc.api.feedback.business.response.BusinessCancelFeedbackResponse;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.helper.exception.BusinessException;

@Service
public class FeedbackDelegateImpl implements FeedbackDelegate {
	private static final Logger LOG = Logger.getLogger(FeedbackDelegateImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessAskFeedbackResponse businessAskFeedback(BusinessAskFeedbackRequest request) {
		BusinessAskFeedbackResponse response = businessAskFeedbackResponse();
		return response;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessCancelFeedbackResponse businessCancelFeedback(BusinessCancelFeedbackRequest request)
			throws BusinessException {
		BusinessCancelFeedbackResponse response = businessCancelFeedbackResponse();
		return response;
	}
	
	//FIXME : dummy response, replace with actual logic
	private BusinessAskFeedbackResponse businessAskFeedbackResponse() {
		BusinessAskFeedbackResponse response = new BusinessAskFeedbackResponse();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		userDetails.setId(41);
		userDetails.setImageUrl("https://scontent.xx.fbcdn.net/v/t1.0-1/c1.0.160.160/p160x160/15578891_1180564885332226_632797692936181444_n.jpg?oh=7834859a26b7b40c9801ad1e563e9015&oe=58FF1D94");
		userDetails.setName("Akash Nigam");
		userDetails.setUserCheckins(27);
		response.setUser(userDetails);
		response.setCardNumber(32);
		response.setCheckinStatus(CheckinStatusType.APPROVED);
		response.setRewardStatus(RewardStatusType.GIVEN);
		response.setRewardMessage("Won an amazing meal voucher of Rs 500!");
		response.setFeedbackStatus(FeedbackStatusType.ASKED);
		return response;
	}
	
	//FIXME : dummy response, replace with actual logic
		private BusinessCancelFeedbackResponse businessCancelFeedbackResponse() {
			BusinessCancelFeedbackResponse response = new BusinessCancelFeedbackResponse();
			UserDetailsResponse userDetails = new UserDetailsResponse();
			userDetails.setId(41);
			userDetails.setImageUrl("https://scontent.xx.fbcdn.net/v/t1.0-1/c1.0.160.160/p160x160/15578891_1180564885332226_632797692936181444_n.jpg?oh=7834859a26b7b40c9801ad1e563e9015&oe=58FF1D94");
			userDetails.setName("Akash Nigam");
			userDetails.setUserCheckins(27);
			response.setUser(userDetails);
			response.setCardNumber(32);
			response.setCheckinStatus(CheckinStatusType.APPROVED);
			response.setRewardStatus(RewardStatusType.GIVEN);
			response.setRewardMessage("Won an amazing meal voucher of Rs 500!");
			response.setFeedbackStatus(FeedbackStatusType.NOT_ASKED);
			return response;
		}
}
