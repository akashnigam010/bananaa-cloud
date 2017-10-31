package in.socyal.sc.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.location.dto.CityDto;
import in.socyal.sc.persistence.cache.BnaCacheManager;
import in.socyal.sc.persistence.entity.CityEntity;
import in.socyal.sc.persistence.mapper.DishDaoMapper;
import in.socyal.sc.persistence.mapper.LocationDaoMapper;

@Repository
public class CacheDao {
	@Autowired
	BnaCacheManager cacheManager;
	@Autowired
	DishDaoMapper dishMapper;
	@Autowired
	LocationDaoMapper locationMapper;
	@Autowired
	SessionFactory sessionFactory;

	public CacheDao() {
	}

	public List<CityDto> getCities() {
		List<CityEntity> cities = cacheManager.getCities();
		// map only active entities
		return locationMapper.map(cities);
	}
}
