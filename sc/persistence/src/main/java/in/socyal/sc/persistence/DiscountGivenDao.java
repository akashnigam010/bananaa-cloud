package in.socyal.sc.persistence;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.DiscountGivenEntity;

@Repository
public class DiscountGivenDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired Clock clock;

	public DiscountGivenDao() {
	}

	public DiscountGivenDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void saveDiscountGiven(BigDecimal amount, Integer checkinId) {
		DiscountGivenEntity entity = new DiscountGivenEntity();
		entity.setCheckinId(checkinId);
		entity.setAmount(amount);
		entity.setDiscountDateTime(clock.cal());
		sessionFactory.getCurrentSession().save(entity);
	}
}
