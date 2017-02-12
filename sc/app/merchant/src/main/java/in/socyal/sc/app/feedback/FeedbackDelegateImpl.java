package in.socyal.sc.app.feedback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinFilterCriteria;
import in.socyal.sc.api.feedback.request.FeedbackRequest;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.response.RewardStatusResponse;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.app.checkin.mapper.CheckinDelegateMapper;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.FeedbackDao;

@Service
public class FeedbackDelegateImpl implements FeedbackDelegate {
	private static final String DEFAULT_REWARD_MESSAGE = "You won a surprise gift! Please ask for it at the reception";

	@Autowired
	FeedbackDao dao;
	@Autowired
	CheckinDelegateMapper mapper;
	@Autowired
	CheckinDao checkinDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public RewardStatusResponse dismissFeedback(FeedbackRequest request) throws BusinessException {
		RewardStatusResponse response = new RewardStatusResponse();
		CheckinFilterCriteria filter = new CheckinFilterCriteria(false, false, false);
		CheckinDto checkin = dao.saveFeedbackStatus(request.getCheckinId(), FeedbackStatusType.RECEIVED, filter);
		checkAndSetRewardDetails(checkin, response);
		//TODO : notify merchant asynchronously - See if required
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public RewardStatusResponse submitFeedback(SubmitFeedbackRequest request) throws BusinessException {
		RewardStatusResponse response = new RewardStatusResponse();
		CheckinFilterCriteria filter = new CheckinFilterCriteria(false, false, false);
		CheckinDto checkin = dao.saveFeedback(request.getCheckinId(), request, filter);
		checkAndSetRewardDetails(checkin, response);
		//TODO : notify merchant asynchronously
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessCheckinDetailsResponse businessAskFeedback(FeedbackRequest request) {
		CheckinFilterCriteria filter = new CheckinFilterCriteria(true, true, false);
		CheckinDto checkin = dao.saveFeedbackStatus(request.getCheckinId(), FeedbackStatusType.ASKED, filter);
		// FIXME : Notify customer asynchronously
		Integer userCheckinCount = checkinDao.getUserCheckinsCountForAMerchant(checkin.getUser().getId(),
				checkin.getMerchantId());
		return mapper.mapBusinessCheckinDetails(checkin, userCheckinCount);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BusinessCheckinDetailsResponse businessCancelFeedback(FeedbackRequest request) throws BusinessException {
		CheckinFilterCriteria filter = new CheckinFilterCriteria(true, true, false);
		CheckinDto checkin = dao.saveFeedbackStatus(request.getCheckinId(), FeedbackStatusType.NOT_ASKED, filter);
		Integer userCheckinCount = checkinDao.getUserCheckinsCountForAMerchant(checkin.getUser().getId(),
				checkin.getMerchantId());
		return mapper.mapBusinessCheckinDetails(checkin, userCheckinCount);
	}
	
	private void checkAndSetRewardDetails(CheckinDto checkin, RewardStatusResponse response) {
		if (checkin.getRewardStatus() == RewardStatusType.GIVEN) {
			response.setCheckinId(checkin.getId());
			response.setShowReward(true);
			response.setRewardMessage(StringUtils.isNotBlank(checkin.getRewardMessage()) ? checkin.getRewardMessage()
					: DEFAULT_REWARD_MESSAGE);
		} else {
			response.setShowReward(false);
		}
	}
}
