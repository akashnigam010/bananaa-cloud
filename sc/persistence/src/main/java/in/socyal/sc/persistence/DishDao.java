package in.socyal.sc.persistence;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.mapper.DishDaoMapper;

@Repository
public class DishDao {
	private static final String NAME = "name";

	@Autowired
	DishDaoMapper mapper;
	@Autowired
	SessionFactory sessionFactory;

	public DishDao() {
	}

	public DishDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean isDishExists(Integer dishId) {
		DishEntity dish = (DishEntity) sessionFactory.getCurrentSession().get(DishEntity.class, dishId);
		return dish == null ? false : true;
	}

	public List<DishDto> searchDishAtARestaurant(String searchString, Integer merchantId) {
		List<DishDto> dishDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.ilike(NAME, searchString, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		@SuppressWarnings("unchecked")
		List<DishEntity> dishes = (List<DishEntity>) criteria.list();
		if (dishes != null && !dishes.isEmpty()) {
			dishDtos = mapper.map(dishes, null);
			return dishDtos;
		}
		return Collections.emptyList();
	}
}
