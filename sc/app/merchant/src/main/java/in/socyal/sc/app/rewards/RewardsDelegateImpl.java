package in.socyal.sc.app.rewards;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.reward.business.response.GetBusinessRewardsResponse;
import in.socyal.sc.api.rewards.dto.RewardsDto;
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
}
