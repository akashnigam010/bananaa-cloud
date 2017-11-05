package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
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
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.api.merchant.request.SearchMerchantRequest;
import in.socyal.sc.api.type.TagType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.MerchantErrorCodeType;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.TrendingMerchantResultEntity;
import in.socyal.sc.persistence.mapper.MerchantDaoMapper;

@Repository
public class MerchantDao {
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String MINIMUM_TAG_RATING_SEARCH = "minimum.rating.search";
	private static final String NAME = "name";
	private static final Integer RESULTS_PER_PAGE = 10;
	
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	MerchantDaoMapper mapper;

	public MerchantDao() {
	}

	public MerchantDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public MerchantDto getMerchantDetailsById(Integer id, MerchantFilterCriteria filter)
			throws BusinessException {
		MerchantDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		MerchantEntity entity = (MerchantEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new MerchantDto();
			mapper.map(entity, dto, filter);
		} else {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}

		return dto;
	}
	
	public MerchantDto getMerchantDetailsByNameId(String nameId, MerchantFilterCriteria filter)
			throws BusinessException {
		MerchantDto dto = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq("nameId", nameId));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		MerchantEntity entity = (MerchantEntity) criteria.uniqueResult();
		if (entity != null) {
			dto = new MerchantDto();
			mapper.map(entity, dto, filter);
		} else {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}

		return dto;
	}
	
	public Integer getMerchantSearchByTagPages(SearchMerchantRequest request) throws BusinessException {
		Criteria criteria = getMerchantSearchByTagCriteria(request);
		criteria.setProjection(Projections.rowCount());
		Long totalCount = (Long) criteria.uniqueResult();
		if (totalCount <= 0) {
			return 0;
		} else {
			return (int) (totalCount/RESULTS_PER_PAGE + 1);
		}
	}
	
	/**
	 * For mobile app, see {@link #getMerchantsByTagId(SearchMerchantRequest)}}
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public List<MerchantDto> getMerchantsByTagNameId(SearchMerchantRequest request) throws BusinessException {
		List<MerchantDto> merchants = new ArrayList<>();
		Criteria criteria = getMerchantSearchByTagCriteria(request);
		MerchantFilterCriteria filter = null;
		if (request.getType() == TagType.CUISINE) {
			filter = new MerchantFilterCriteria(true);
		} else if (request.getType() == TagType.SUGGESTION) {
			filter = new MerchantFilterCriteria(true);
		}
		int firstResult = ((request.getPage() - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		List<MerchantEntity> entities = criteria.list();
		if (entities != null &&  entities.size() > 0) {
			mapper.map(entities, merchants, filter);
		} 
		return merchants;
	}
	
	/**
	 * For web, see {@link #getMerchantsByTagNameId(SearchMerchantRequest)}}
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public List<MerchantDto> getMerchantsByTagId(SearchMerchantRequest request) throws BusinessException {
		List<MerchantDto> merchants = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		if (request.getType() == TagType.CUISINE) {
			criteria.createAlias("cuisineRatings", "cuisineRatings");
			criteria.add(Restrictions.ge("cuisineRatings.rating", Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_SEARCH))));
			criteria.createAlias("cuisineRatings.cuisine", "cuisine");
			criteria.add(Restrictions.eq("cuisine.id", request.getTagId()));
			criteria.addOrder(Order.desc("cuisineRatings.rating"));
		} else if (request.getType() == TagType.SUGGESTION) {
			criteria.createAlias("suggestionRatings", "suggestionRatings");
			criteria.add(Restrictions.ge("suggestionRatings.rating", Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_SEARCH))));
			criteria.createAlias("suggestionRatings.suggestion", "suggestion");
			criteria.add(Restrictions.eq("suggestion.id", request.getTagId()));
			criteria.addOrder(Order.desc("suggestionRatings.rating"));
		} else {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}

		criteria.createAlias("address", "address");
		criteria.createAlias("address.locality", "locality");
		if (!request.getIsCity()) {
			criteria.add(Restrictions.eq("locality.id", request.getLocationId()));
		} else {
			criteria.createAlias("locality.city", "city");
			criteria.add(Restrictions.eq("city.id", request.getLocationId()));
		}
		MerchantFilterCriteria filter = new MerchantFilterCriteria(true);
		int firstResult = ((request.getPage() - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		List<MerchantEntity> entities = criteria.list();
		if (entities != null &&  entities.size() > 0) {
			mapper.map(entities, merchants, filter);
		} 
		return merchants;
	}
	
	private Criteria getMerchantSearchByTagCriteria(SearchMerchantRequest request) throws BusinessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		if (request.getType() == TagType.CUISINE) {
			criteria.createAlias("cuisineRatings", "cuisineRatings");
			criteria.add(Restrictions.ge("cuisineRatings.rating", Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_SEARCH))));
			criteria.createAlias("cuisineRatings.cuisine", "cuisine");
			criteria.add(Restrictions.eq("cuisine.nameId", request.getNameId()));
			criteria.addOrder(Order.desc("cuisineRatings.rating"));
		} else if (request.getType() == TagType.SUGGESTION) {
			criteria.createAlias("suggestionRatings", "suggestionRatings");
			criteria.add(Restrictions.ge("suggestionRatings.rating", Float.parseFloat(resource.getString(MINIMUM_TAG_RATING_SEARCH))));
			criteria.createAlias("suggestionRatings.suggestion", "suggestion");
			criteria.add(Restrictions.eq("suggestion.nameId", request.getNameId()));
			criteria.addOrder(Order.desc("suggestionRatings.rating"));
		} else {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}

		criteria.createAlias("address", "address");
		criteria.createAlias("address.locality", "locality");
		if (request.getLocalityNameId() != null) {
			criteria.add(Restrictions.eq("locality.nameId", request.getLocalityNameId()));
		} else {
			criteria.createAlias("locality.city", "city");
			criteria.add(Restrictions.eq("city.nameId", request.getCityNameId()));
		}
		return criteria;
	}

	public List<MerchantDto> searchActiveMerchant(String restaurantName, MerchantFilterCriteria filter)
			throws BusinessException {
		List<MerchantDto> merchantDtos = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.ilike(NAME, restaurantName, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("isActive", Boolean.TRUE));
		@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
		// FIXME : Removed Approx name matching for now.Fix later
		//merchants.addAll(searchActiveMerchantApprox(restaurantName));
		if (merchants != null && !merchants.isEmpty()) {
			merchantDtos = new ArrayList<>();
			mapper.map(merchants, merchantDtos, filter);
		}
		return merchantDtos;
	}
	
	@SuppressWarnings("unchecked")
	private List<MerchantEntity> searchActiveMerchantApprox(String restaurantName) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(levenshteinDistanceQuery());
		query.addEntity(MerchantEntity.class);
		query.setString("searchStr", restaurantName);
		int firstResult = 0;
    	query.setFirstResult(firstResult);
    	query.setMaxResults(5);
    	return query.list();
	}
	
	private String levenshteinDistanceQuery() {
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT * FROM ");
    	query.append("(SELECT *, bna.LEVENSHTEIN_RATIO(:searchStr, M.NAME) AS RATIO FROM ");
    	query.append("bna.MERCHANT M ");
    	query.append("WHERE M.IS_ACTIVE = 1 ");
    	query.append("ORDER BY RATIO DESC ) T ");
    	query.append("WHERE T.RATIO > 20");
    	return query.toString();
    }
	
	/**
	 * This method doesn't makes use of levenschtein algorithm. See
	 * searchActiveMerchantApprox for that
	 * 
	 * @param restaurantName
	 * @param filter
	 * @return
	 * @throws BusinessException
	 */
	public List<MerchantDto> searchMerchant(String restaurantName, MerchantFilterCriteria filter)
			throws BusinessException {
		List<MerchantDto> merchantDtos = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.ilike(NAME, restaurantName, MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
		if (merchants != null && !merchants.isEmpty()) {
			merchantDtos = new ArrayList<>();
			mapper.map(merchants, merchantDtos, filter);
		}
		return merchantDtos;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrendingMerchantResultDto> getAllSortedMerchants(LocationCookieDto cookieDto,
			MerchantFilterCriteria filter, Integer page, Integer resultsPerPage) {
		List<TrendingMerchantResultDto> response = new ArrayList<>();
		Criteria criteria = getAllSortedMerchantsCriteria(cookieDto);
		int firstResult = ((page - 1) * resultsPerPage);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(resultsPerPage);
		List<TrendingMerchantResultEntity> result = (List<TrendingMerchantResultEntity>) criteria.list();
		mapper.map(result, response, filter);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getAllSortedMerchantsPages(LocationCookieDto cookieDto) {
		Criteria criteria = getAllSortedMerchantsCriteria(cookieDto);
		List<Long> totalCountList = criteria.list();
		if (totalCountList.size() <= 0) {
			return 0;
		} else {
			return (int) (totalCountList.size() / RESULTS_PER_PAGE + 1);
		}
	}
	
	private Criteria getAllSortedMerchantsCriteria(LocationCookieDto cookieDto) {
		// Trending restaurant criteria is calculated using average of DISH rating
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.add(Restrictions.ge("rating", 3.0));
		criteria.createAlias("merchant", "merchant");
		criteria.add(Restrictions.eq("merchant.isActive", Boolean.TRUE));
		if (StringUtils.isNotBlank(cookieDto.getSearchString())) {
			criteria.add(Restrictions.ilike("merchant.name", cookieDto.getSearchString(), MatchMode.ANYWHERE));
		}
		criteria.createAlias("merchant.address", "address");
		criteria.createAlias("address.locality", "locality");
		if (cookieDto.isSearchById()) {
			if (cookieDto.getIsCity()) {
				criteria.createAlias("locality.city", "city");
				criteria.add(Restrictions.eq("city.id", cookieDto.getId()));
			} else {
				criteria.add(Restrictions.eq("locality.id", cookieDto.getId()));
			}
		} else {
			if (cookieDto.isCitySearch()) {
				criteria.createAlias("locality.city", "city");
				criteria.add(Restrictions.eq("city.nameId", cookieDto.getCityId()));
			} else {
				criteria.add(Restrictions.eq("locality.nameId", cookieDto.getLocalityId()));
			}
		}	
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.avg("rating").as("rating"));
		projList.add(Projections.groupProperty("merchant.id"));
		projList.add(Projections.property("merchant").as("merchant"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("rating"));
		criteria.setResultTransformer(Transformers.aliasToBean(TrendingMerchantResultEntity.class));
		return criteria;
	}
}
