package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.mapper.LocationDaoMapper;

@Repository
public class LocationDao {
	private static Integer RESULTS_PER_PAGE = 10;
	private static final String NAME = "name";

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	LocationDaoMapper mapper;

	public LocationDao() {
	}

	public LocationDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<LocalityDto> getLocalities() {
		List<LocalityDto> localityDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LocalityEntity.class);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<LocalityEntity> localities = (List<LocalityEntity>) criteria.list();
		if (localities != null && !localities.isEmpty()) {
			localityDtos = new ArrayList<>();
			mapper.map(localities, localityDtos);
		}
		return localityDtos;
	}

	@Transactional
	public List<LocalityDto> searchLocalities(String searchString) {
		List<LocalityDto> localityDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LocalityEntity.class);
		criteria.add(Restrictions.ilike(NAME, searchString, MatchMode.ANYWHERE));
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<LocalityEntity> localities = (List<LocalityEntity>) criteria.list();
		if (localities != null && !localities.isEmpty()) {
			localityDtos = new ArrayList<>();
			mapper.map(localities, localityDtos);
		}
		return localityDtos;
	}
}
