package in.socyal.sc.persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.reward.request.Reward;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.RewardGivenEntity;

@Repository
public class RewardGivenDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired Clock clock;

	public RewardGivenDao() {
	}

	public RewardGivenDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void saveRewardGiven(Reward reward, Integer checkinId) {
		RewardGivenEntity entity = new RewardGivenEntity();
		entity.setCheckinId(checkinId);
		entity.setRewardId(reward.getId());
		entity.setCount(reward.getQuantity());
		entity.setRewardDateTime(clock.cal());
		sessionFactory.getCurrentSession().save(entity);
	}
}
