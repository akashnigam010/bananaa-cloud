package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.merchant.dto.CityDto;
import in.socyal.sc.api.merchant.dto.LocalityDto;
import in.socyal.sc.persistence.entity.CityEntity;
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
	
	public Map<String, CityDto> getCityCache() {
		Map<String, CityDto> map = new HashMap<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CityEntity.class);
		@SuppressWarnings("unchecked")
		List<CityEntity> cities = (List<CityEntity>) criteria.list();
		if (cities != null && !cities.isEmpty()) {
			for (CityEntity entity : cities) {
				CityDto city = mapper.map(entity);
				map.put(city.getNameId(), city);
			}
		}
		return map;
	}
	
	public Map<String, LocalityDto> getLocalityCache() {
		Map<String, LocalityDto> map = new HashMap<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LocalityEntity.class);
		@SuppressWarnings("unchecked")
		List<LocalityEntity> localities = (List<LocalityEntity>) criteria.list();
		if (localities != null && !localities.isEmpty()) {
			for (LocalityEntity entity : localities) {
				LocalityDto locality = mapper.map(entity);
				map.put(locality.getNameId(), locality);
			}
		}
		return map;
	}
}
