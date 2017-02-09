package in.socyal.sc.app.rewards;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.checkin.business.response.BusinessApproveCheckinResponse;
import in.socyal.sc.api.checkin.response.UserDetailsResponse;
import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;
import in.socyal.sc.api.reward.response.SubmitRewardsResponse;
import in.socyal.sc.api.rewards.dto.RewardsDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.app.rewards.mapper.RewardsDelegateMapper;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.persistence.RewardsDao;

@Service
public class RewardsDelegateImpl implements RewardsDelegate {
	private static final Logger LOG = Logger.getLogger(RewardsDelegateImpl.class);
	@Autowired
	RewardsDao dao;
	@Autowired
	RewardsDelegateMapper mapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public GetBusinessRewardsResponse getRewardsList(Integer merchantId) throws BusinessException {
		GetBusinessRewardsResponse response = new GetBusinessRewardsResponse();
		List<RewardsDto> result = dao.fetchRewardsList(merchantId);
		mapper.map(result, response);
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SubmitRewardsResponse submitRewards(SubmitRewardsRequest request) throws BusinessException {
		SubmitRewardsResponse response = new SubmitRewardsResponse();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		userDetails.setId(6);
		userDetails.setImageUrl("https://scontent.xx.fbcdn.net/v/t1.0-1/c1.0.160.160/p160x160/15578891_1180564885332226_632797692936181444_n.jpg?oh=7834859a26b7b40c9801ad1e563e9015&oe=58FF1D94");
		userDetails.setName("Akash Nigam");
		userDetails.setUserCheckins(54);
		response.setUser(userDetails);
		response.setCardNumber(4);
		response.setCheckinStatus(CheckinStatusType.APPROVED);
		response.setRewardStatus(RewardStatusType.GIVEN);
		response.setRewardMessage("Won amazon gift voucher worth Rs. 1500!");
		response.setFeedbackStatus(FeedbackStatusType.NOT_ASKED);
		return response;
	}
}
