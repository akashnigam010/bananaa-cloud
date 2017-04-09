package in.socyal.sc.persistence;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.persistence.entity.DishEntity;

@Repository
public class DishDao {
	private static final Logger LOG = Logger.getLogger(DishDao.class);
	@Autowired
	SessionFactory sessionFactory;

	public DishDao() {
	}

	public DishDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean isDishExists(Integer dishId) {
		DishEntity dish = (DishEntity) sessionFactory.getCurrentSession().get(DishEntity.class, dishId);
		if (dish == null) {
			return false;
		} else {
			return true;
		}
	}
}
