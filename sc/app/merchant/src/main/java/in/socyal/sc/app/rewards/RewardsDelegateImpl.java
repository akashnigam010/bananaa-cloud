package in.socyal.sc.app.rewards;

import java.math.BigDecimal;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.checkin.business.response.BusinessCheckinDetailsResponse;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinFilterCriteria;
import in.socyal.sc.api.feedback.response.FeedbackStatusResponse;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.reward.request.Reward;
import in.socyal.sc.api.reward.request.RewardRequest;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;
import in.socyal.sc.api.rewards.dto.RewardsDto;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.app.checkin.mapper.CheckinDelegateMapper;
import in.socyal.sc.app.rewards.mapper.RewardsDelegateMapper;
import in.socyal.sc.helper.async.AsyncExecutor;
import in.socyal.sc.helper.async.SimpleCallable;
import in.socyal.sc.notification.NotificationCreator;
import in.socyal.sc.notification.NotificationDelegate;
import in.socyal.sc.persistence.CheckinDao;
import in.socyal.sc.persistence.DiscountGivenDao;
import in.socyal.sc.persistence.RewardGivenDao;
import in.socyal.sc.persistence.RewardsDao;

@Service
public class RewardsDelegateImpl implements RewardsDelegate {
	private static final Logger LOG = Logger.getLogger(RewardsDelegateImpl.class);
	@Autowired
	RewardsDao dao;
	@Autowired
	CheckinDao checkinDao;
	@Autowired
	RewardsDelegateMapper mapper;
	@Autowired
	CheckinDelegateMapper checkinMapper;
	@Autowired
	NotificationDelegate notificationDelegate;
	@Autowired
	NotificationCreator notificationCreator;
	@Autowired
	AsyncExecutor async;
	@Autowired
	RewardGivenDao rewardGivenDao;
	@Autowired
	DiscountGivenDao discountGivenDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public GetBusinessRewardsResponse getRewardsList(Integer merchantId) throws BusinessException {
		GetBusinessRewardsResponse response = new GetBusinessRewardsResponse();
		List<RewardsDto> result = dao.fetchRewardsList(merchantId);
		mapper.map(result, response);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public BusinessCheckinDetailsResponse submitRewards(SubmitRewardsRequest request) throws BusinessException {
		CheckinFilterCriteria filter = new CheckinFilterCriteria(true, true, false);
		//1. Save rewards and discount in REWARD_GIVEN and DISCOUNT_GIVEN tables respectively
		Integer checkinId = request.getCheckinId();
		List<Reward> rewards = request.getRewards();
		if (rewards != null) {
			for (Reward reward : rewards) {
			rewardGivenDao.saveRewardGiven(reward, checkinId);
			}
		}
		
		if (request.getDiscount() != null) {
			discountGivenDao.saveDiscountGiven(new BigDecimal(request.getDiscount()), checkinId);
		}
		//2. Update checkin table with actual rewards and discount details
		CheckinDto checkin = dao.saveReward(request, filter);
		Integer userCheckinCount = checkinDao.getUserCheckinsCountForAMerchant(checkin.getUser().getId(),
				checkin.getMerchantId());
		async.submit(new SimpleCallable<Void>() {
			@Override
			public Void call() throws Exception {
				notificationDelegate.sendDataNotification(notificationCreator.createSubmitRewardNotificationToCustomer(checkin));
				return null;
			}
		});
		return checkinMapper.mapBusinessCheckinDetails(checkin, userCheckinCount);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public FeedbackStatusResponse dismissReward(RewardRequest request) throws BusinessException {
		FeedbackStatusResponse response = new FeedbackStatusResponse();
		CheckinFilterCriteria filter = new CheckinFilterCriteria(true, false, false);
		CheckinDto checkin = dao.saveRewardStatus(request.getCheckinId(), RewardStatusType.SEEN, filter);
		checkAndSetFeedbackDetails(checkin, response);
		return response;
	}

	private void checkAndSetFeedbackDetails(CheckinDto checkin, FeedbackStatusResponse response) {
		if (checkin.getFeedback().getStatus() == FeedbackStatusType.ASKED) {
			response.setCheckinId(checkin.getId());
			response.setShowFeedback(true);
			response.setMerchantName(checkin.getMerchantQrMapping().getMerchant().getName());
			response.setShortAddress(
					checkin.getMerchantQrMapping().getMerchant().getAddress().getLocality().getShortAddress());
		} else {
			response.setShowFeedback(false);
		}
	}
}
