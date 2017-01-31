package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.rewards.dto.RewardsDto;
import in.socyal.sc.persistence.entity.RewardsEntity;
import in.socyal.sc.persistence.mapper.RewardsDaoMapper;

@Repository
public class RewardsDao {
	private static final Logger LOG = Logger.getLogger(RewardsDao.class);
	private static final Integer RESULTS_PER_PAGE = 10;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	RewardsDaoMapper mapper;

	public RewardsDao() {
	}

	public RewardsDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<RewardsDto> fetchRewardsList(Integer merchantId) {
		List<RewardsDto> rewards = Collections.emptyList();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RewardsEntity.class);
		criteria.add(Restrictions.eq(RewardsEntity.MERCHANT_ID, merchantId));
		@SuppressWarnings("unchecked")
		List<RewardsEntity> result = criteria.list();
		if (result.size() > 0) {
			rewards = new ArrayList<>();
			mapper.map(result, rewards);
		}
		return rewards;
	}
}
