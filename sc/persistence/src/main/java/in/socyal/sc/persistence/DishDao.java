package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.cache.dto.LocationCookieDto;
import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.dish.dto.DishFilterCriteria;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.item.response.PopularTag;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.response.GlobalSearchItem;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.DishErrorCodeType;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishCount;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.MerchantCuisineRatingEntity;
import in.socyal.sc.persistence.entity.MerchantSuggestionRatingEntity;
import in.socyal.sc.persistence.entity.MerchantWrapperEntity;
import in.socyal.sc.persistence.entity.PopularTagEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.mapper.DishDaoMapper;
import in.socyal.sc.persistence.mapper.MerchantDaoMapper;
import in.socyal.sc.persistence.mapper.RecommendationDaoMapper;

@Repository
public class DishDao {
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String NAME = "name";
	private static final Integer RESULTS_PER_PAGE = 10;
	private static final String MINIMUM_TAG_RATING_SEARCH = "minimum.rating.search";

	@Autowired
	DishDaoMapper mapper;
	@Autowired
	RecommendationDaoMapper rcmdnMapper;
	@Autowired
	MerchantDaoMapper merchantDaoMapper;
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

	@SuppressWarnings("unchecked")
	public Integer searchDishByNamePages(String searchString, LocationCookieDto cookieDto) {
		Criteria criteria = searchDishByNameCriteria(searchString, cookieDto);
		List<Long> totalCountList = criteria.list();
		if (totalCountList.size() <= 0) {
			return 0;
		} else {
			return (int) (totalCountList.size() / RESULTS_PER_PAGE + 1);
		}
	}

	@SuppressWarnings("unchecked")
	public List<MerchantDto> searchDishByName(String searchString, LocationCookieDto cookieDto, Integer page) {
		Criteria criteria = searchDishByNameCriteria(searchString, cookieDto);
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		criteria.setResultTransformer(Transformers.aliasToBean(MerchantWrapperEntity.class));
		List<MerchantWrapperEntity> merchants = criteria.list();
		if (merchants != null && !merchants.isEmpty()) {
			List<MerchantDto> merchantDtos = new ArrayList<>();
			MerchantFilterCriteria filter = new MerchantFilterCriteria(true, true, true, true, false, true);
			MerchantDto dto = null;
			for (MerchantWrapperEntity wrapper : merchants) {
				dto = new MerchantDto();
				merchantDaoMapper.map(wrapper.getMerchant(), dto, filter);
				merchantDtos.add(dto);
			}
			return merchantDtos;
		}
		return Collections.emptyList();
	}

	private Criteria searchDishByNameCriteria(String searchString, LocationCookieDto cookieDto) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.ilike(NAME, searchString, MatchMode.ANYWHERE));
		criteria.createAlias("merchant", "merchant");
		criteria.add(Restrictions.eq("merchant.isActive", Boolean.TRUE));
		criteria.createAlias("merchant.address", "address");
		criteria.createAlias("address.locality", "locality");
		if (cookieDto.isCitySearch()) {
			criteria.createAlias("locality.city", "city");
			criteria.add(Restrictions.eq("city.nameId", cookieDto.getCityId()));
		} else {
			criteria.add(Restrictions.eq("locality.nameId", cookieDto.getLocalityId()));
		}

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("merchant").as("merchant"));
		projList.add(Projections.groupProperty("merchant.id"));
		criteria.setProjection(projList);
		return criteria;
	}

	
	/**
	 * This method makes use of levenschtein algorithm to search dishes at a restaurant
	 * 
	 * @param restaurantName
	 * @param filter
	 * @return
	 * @throws BusinessException
	 */
	public List<DishDto> searchDishAtARestaurant(String searchString, Integer merchantId) {
		List<DishDto> dishDtos = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.ilike(NAME, searchString, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		@SuppressWarnings("unchecked")
		List<DishEntity> dishes = (List<DishEntity>) criteria.list();
		dishes.addAll(searchDishAtARestaurantApprox(searchString, merchantId));
		if (dishes != null && !dishes.isEmpty()) {
			DishFilterCriteria dishCriteria = new DishFilterCriteria(Boolean.FALSE);
			MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(Boolean.FALSE);
			dishDtos = mapper.map(dishes, merchantCriteria, dishCriteria);
			return dishDtos;
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	private List<DishEntity> searchDishAtARestaurantApprox(String searchString, Integer merchantId) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(levenshteinDistanceQueryDish());
		query.addEntity(DishEntity.class);
		query.setString("searchStr", searchString);
		query.setInteger("merchantId", merchantId);
		int firstResult = 0;
		query.setFirstResult(firstResult);
		query.setMaxResults(3);
		return query.list();
	}

	private String levenshteinDistanceQueryDish() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ");
		query.append("(SELECT *, bna.LEVENSHTEIN_RATIO(:searchStr, D.NAME) AS RATIO FROM ");
		query.append("bna.DISH D ");
		query.append("WHERE D.MERCHANT_ID = :merchantId ");
		query.append("ORDER BY RATIO DESC ) T ");
		query.append("WHERE T.RATIO > 20");
		return query.toString();
	}

	public List<DishDto> getPopularItems(Integer merchantId, Integer page, Integer resultsPerPage) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.addOrder(Order.desc("rating"));
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		DishFilterCriteria dishCriteria = new DishFilterCriteria(false, false, true, true);
		MerchantFilterCriteria merchantCriteria = new MerchantFilterCriteria(Boolean.FALSE);
		return getItems(criteria, merchantCriteria, dishCriteria);
	}

	@SuppressWarnings("unchecked")
	private List<DishDto> getItems(Criteria criteria, MerchantFilterCriteria merchantCriteria,
			DishFilterCriteria dishCriteria) {
		List<DishDto> dishDtos = null;
		List<DishEntity> dishes = criteria.list();
		if (dishes != null && !dishes.isEmpty()) {
			dishDtos = mapper.map(dishes, merchantCriteria, dishCriteria);
			return dishDtos;
		}
		return Collections.emptyList();
	}

	public DishDto getItemDetailsById(Integer id, DishFilterCriteria dishCriteria) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		DishEntity entity = (DishEntity) criteria.uniqueResult();
		if (entity == null) {
			throw new BusinessException(DishErrorCodeType.DISH_DETAILS_NOT_FOUND);
		}
		MerchantFilterCriteria filterCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		return mapper.map(entity, filterCriteria, dishCriteria);
	}
	
	public DishDto getItemDetailsByNameId(String merchantNameId, String dishNameId, DishFilterCriteria dishCriteria) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.createAlias("merchant", "m");
		criteria.add(Restrictions.eq("m.nameId", merchantNameId));
		criteria.add(Restrictions.eq("nameId", dishNameId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		DishEntity entity = (DishEntity) criteria.uniqueResult();
		if (entity == null) {
			throw new BusinessException(DishErrorCodeType.DISH_DETAILS_NOT_FOUND);
		}
		MerchantFilterCriteria filterCriteria = new MerchantFilterCriteria(Boolean.FALSE, Boolean.TRUE);
		return mapper.map(entity, filterCriteria, dishCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<GlobalSearchItem> searchTags(String searchString, Integer page, Integer resultsPerPage,
			TagType tagType) {
		Criteria criteria = null;
		if (tagType == TagType.CUISINE) {
			criteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
		} else if (tagType == TagType.SUGGESTION) {
			criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		}
		criteria.add(Restrictions.ilike(NAME, searchString, MatchMode.ANYWHERE));
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		return mapper.mapTagsShortDetails(criteria.list());
	}
	
	@SuppressWarnings("unchecked")
	public List<PopularTag> getPopularCuisines(LocationCookieDto cookieDto, Integer page, Integer resultsPerPage)
			throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantCuisineRatingEntity.class);
		criteria.createAlias("merchant", "merchant");
		criteria.add(Restrictions.gt("rating", Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_SEARCH))));
		criteria.createAlias("merchant.address", "address");
		criteria.createAlias("address.locality", "locality");
		String preUrl = null;
		if (cookieDto.isCitySearch()) {
			criteria.createAlias("locality.city", "city");
			criteria.add(Restrictions.eq("city.nameId", cookieDto.getCityId()));
			preUrl = "/" + cookieDto.getCityId() + "/";
		} else {
			criteria.add(Restrictions.eq("locality.nameId", cookieDto.getLocalityId()));
			preUrl = "/" + cookieDto.getCityId() + "/" + cookieDto.getLocalityId() + "/";
		}
		criteria.createAlias("cuisine", "cuisine");
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("cuisine.id").as("merchants"));
		projList.add(Projections.groupProperty("cuisine.id"));
		projList.add(Projections.property("cuisine.name").as("name"));
		projList.add(Projections.property("cuisine.nameId").as("nameId"));
		projList.add(Projections.property("cuisine.thumbnail").as("thumbnail"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("merchants"));
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		criteria.setResultTransformer(Transformers.aliasToBean(PopularTagEntity.class));
		List<PopularTagEntity> entities = (List<PopularTagEntity>) criteria.list();
		return mapper.mapPopularTags(entities, preUrl);
	}

	@SuppressWarnings("unchecked")
	public List<PopularTag> getPopularSuggestions(LocationCookieDto cookieDto, Integer page, Integer resultsPerPage)
			throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantSuggestionRatingEntity.class);
		criteria.createAlias("merchant", "merchant");
		criteria.add(Restrictions.gt("rating", Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_SEARCH))));
		criteria.createAlias("merchant.address", "address");
		criteria.createAlias("address.locality", "locality");
		String preUrl = null;
		if (cookieDto.isCitySearch()) {
			criteria.createAlias("locality.city", "city");
			criteria.add(Restrictions.eq("city.nameId", cookieDto.getCityId()));
			preUrl = "/" + cookieDto.getCityId() + "/";
		} else {
			criteria.add(Restrictions.eq("locality.nameId", cookieDto.getLocalityId()));
			preUrl = "/" + cookieDto.getCityId() + "/" + cookieDto.getLocalityId() + "/";
		}
		criteria.createAlias("suggestion", "suggestion");
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("suggestion.id").as("merchants"));
		projList.add(Projections.groupProperty("suggestion.id"));
		projList.add(Projections.property("suggestion.name").as("name"));
		projList.add(Projections.property("suggestion.nameId").as("nameId"));
		projList.add(Projections.property("suggestion.thumbnail").as("thumbnail"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("merchants"));
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		criteria.setResultTransformer(Transformers.aliasToBean(PopularTagEntity.class));
		List<PopularTagEntity> entities = (List<PopularTagEntity>) criteria.list();
		return mapper.mapPopularTags(entities, preUrl);
	}

	@SuppressWarnings("unchecked")
	public List<DishCount> getCuisineDishCount(Integer merchantId, List<Integer> cuisineIds) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		criteria.createAlias("cuisines", "cuisine");
		criteria.createAlias("merchant", "merchant");
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.in("cuisine.id", cuisineIds));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("cuisine.id").as("cuisineId"));
		projList.add(Projections.count("cuisine.id").as("count"));
		criteria.setProjection(projList);
		criteria.setResultTransformer(Transformers.aliasToBean(DishCount.class));
		List<DishCount> entities = (List<DishCount>) criteria.list();
		return entities;
	}
	
	/**
	 * To be used for caching purpose only
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CuisineEntity> getAllCuisines() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
		criteria.addOrder(Order.asc("id"));
		List<CuisineEntity> cuisines = criteria.list();
		if (cuisines != null) {
			return cuisines;
		} else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * To be used for caching purpose only
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SuggestionEntity> getAllSuggestions() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		criteria.addOrder(Order.asc("id"));
		List<SuggestionEntity> suggestions = criteria.list();
		if (suggestions != null) {
			return suggestions;
		} else {
			return Collections.emptyList();
		}
	}
}
