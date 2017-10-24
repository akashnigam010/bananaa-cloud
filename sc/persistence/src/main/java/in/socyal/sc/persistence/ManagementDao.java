package in.socyal.sc.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.dish.dto.ItemImageDto;
import in.socyal.sc.api.engine.request.IdRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.manage.request.AddItemRequest;
import in.socyal.sc.api.manage.request.DishVegnonvegValue;
import in.socyal.sc.api.manage.request.DishVegnonvegValuesRequest;
import in.socyal.sc.api.manage.request.MerchantFlagsRequest;
import in.socyal.sc.api.manage.request.NewMerchantRequest;
import in.socyal.sc.api.manage.request.UpdateItemRequest;
import in.socyal.sc.api.manage.response.Item;
import in.socyal.sc.api.manage.response.MerchantFlagsResponse;
import in.socyal.sc.api.merchant.request.SearchRequest;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.api.type.error.DishErrorCodeType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.MerchantErrorCodeType;
import in.socyal.sc.persistence.entity.CuisineEntity;
import in.socyal.sc.persistence.entity.DishEntity;
import in.socyal.sc.persistence.entity.ItemImageEntity;
import in.socyal.sc.persistence.entity.LocalityEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.SuggestionEntity;
import in.socyal.sc.persistence.mapper.DishDaoMapper;
import in.socyal.sc.persistence.mapper.ManagementDaoMapper;

@Repository
public class ManagementDao {
	private ResourceBundle resource = ResourceBundle.getBundle("environment");
	private static final String BNA_USER_ID = "user.id";
	private static final String NAME = "name";
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ManagementDaoMapper mapper;

	@Autowired
	DishDaoMapper dishDaoMapper;

	public ManagementDao() {
	}

	public ManagementDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Item> getAllItems(IdRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DishEntity.class);
		criteria.createAlias("merchant", "merchant");
		criteria.add(Restrictions.eq("merchant.id", request.getId()));
		List<DishEntity> entities = criteria.list();
		if (entities != null) {
			return mapper.map(entities);
		}
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	public void addItem(AddItemRequest request) throws BusinessException {
		MerchantEntity merchant = getMerchantById(request.getMerchantId());
		canDishesBeUpdated(merchant);
		Criteria suggestionCriteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		suggestionCriteria.add(Restrictions.in("id", request.getSuggestionIds()));
		Criteria cuisineCriteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
		cuisineCriteria.add(Restrictions.in("id", request.getCuisineIds()));
		DishEntity entity = mapper.map(request, merchant);
		if (request.getSuggestionIds().size() > 0) {
			entity.setSuggestions(suggestionCriteria.list());
		}
		if (request.getCuisineIds().size() > 0) {
			entity.setCuisines(cuisineCriteria.list());
		}
		sessionFactory.getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	public void updateItem(UpdateItemRequest request) throws BusinessException {
		DishEntity dish = (DishEntity) sessionFactory.getCurrentSession().get(DishEntity.class, request.getId());
		if (dish == null) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		canDishesBeUpdated(dish.getMerchant());
		dish.setName(request.getName());
		dish.setNameId(request.getNameId());
		dish.setVegnonveg(request.getVegnonveg());
		dish.setThumbnail(request.getThumbnail());
		dish.setImageUrl(request.getImageUrl());
		dish.setIsActive(request.getIsActive());
		List<Integer> suggestionIds = request.getSuggestionIds();
		List<Integer> cuisineIds = request.getCuisineIds();

		if (suggestionIds.size() > 0) {
			Criteria suggestionCriteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
			suggestionCriteria.add(Restrictions.in("id", suggestionIds));
			dish.setSuggestions(suggestionCriteria.list());
		} else {
			dish.setSuggestions(null);
		}

		if (cuisineIds.size() > 0) {
			Criteria cuisineCriteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
			cuisineCriteria.add(Restrictions.in("id", cuisineIds));
			dish.setCuisines(cuisineCriteria.list());
		} else {
			dish.setCuisines(null);
		}

		sessionFactory.getCurrentSession().save(dish);
	}

	@SuppressWarnings("unchecked")
	public void deleteItem(IdRequest request) throws BusinessException {
		DishEntity dish = (DishEntity) sessionFactory.getCurrentSession().get(DishEntity.class, request.getId());
		if (dish == null) {
			throw new BusinessException(DishErrorCodeType.DISH_ID_NOT_FOUND);
		}
		canDishesBeUpdated(dish.getMerchant());
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.createAlias("dish", "dish");
		criteria.add(Restrictions.eq("dish.id", request.getId()));
		List<RecommendationEntity> rcmds = criteria.list();
		if (rcmds != null) {
			for (RecommendationEntity rcmd : rcmds) {
				sessionFactory.getCurrentSession().delete(rcmd);
			}
		}

		dish.setCuisines(null);
		dish.setSuggestions(null);
		sessionFactory.getCurrentSession().delete(dish);
	}

	public void addRecommendations(Integer id, Float rating, Integer rcmdCount) throws BusinessException {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
		try {
			sessionFactory.getCurrentSession().doWork(new Work() {
				@Override
				public void execute(Connection con) throws SQLException {
					PreparedStatement st = con.prepareStatement(
							"INSERT INTO `bna`.`recommendation` (`DISH_ID`, `USER_ID`, `RATING`, `CREATED_DATETIME`, `UPDATED_DATETIME`) VALUES (?, ?, ?, ?, ?)");
					for (int i = 1; i <= rcmdCount; i++) {
						st.setInt(1, id);
						st.setInt(2, Integer.parseInt(resource.getString(BNA_USER_ID)));
						st.setFloat(3, rating);
						st.setTimestamp(4, ts);
						st.setTimestamp(5, ts);
						st.addBatch();
					}

					st.executeBatch();
				}
			});
		} catch (Throwable e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	public void addCuisine(String name) {
		Calendar cal = Calendar.getInstance();
		CuisineEntity entity = new CuisineEntity(cal, cal);
		entity.setName(name);
		entity.setNameId(generateNameId(name));
		sessionFactory.getCurrentSession().save(entity);
	}

	public void addSuggestion(String name) {
		Calendar cal = Calendar.getInstance();
		SuggestionEntity entity = new SuggestionEntity(cal, cal);
		entity.setName(name);
		entity.setNameId(generateNameId(name));
		sessionFactory.getCurrentSession().save(entity);
	}

	public List<CuisineDto> getCuisines(SearchRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CuisineEntity.class);
		criteria.add(Restrictions.ilike(NAME, request.getSearchString(), MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<CuisineEntity> entities = criteria.list();
		return dishDaoMapper.mapCuisines(entities);
	}

	public List<SuggestionDto> getSuggestions(SearchRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SuggestionEntity.class);
		criteria.add(Restrictions.ilike(NAME, request.getSearchString(), MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<SuggestionEntity> entities = criteria.list();
		return dishDaoMapper.mapSuggestions(entities);
	}

	public List<ItemImageDto> getItemImages(SearchRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ItemImageEntity.class);
		criteria.add(Restrictions.ilike(NAME, request.getSearchString(), MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<ItemImageEntity> entities = criteria.list();
		return mapper.mapItemImages(entities);
	}

	public void updateDishVegnonvegValues(DishVegnonvegValuesRequest request) throws BusinessException {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
		try {
			sessionFactory.getCurrentSession().doWork(new Work() {
				@Override
				public void execute(Connection con) throws SQLException {
					PreparedStatement st = con.prepareStatement(
							"UPDATE `bna`.`dish` set `VEGNONVEG` = ?, `UPDATED_DATETIME` = ? WHERE `ID` = ?");
					for (DishVegnonvegValue value : request.getValues()) {
						st.setInt(1, value.getValue());
						st.setTimestamp(2, ts);
						st.setInt(3, value.getId());
						st.addBatch();
					}

					st.executeBatch();
				}
			});
		} catch (Throwable e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	public MerchantFlagsResponse getActiveAndEditFlags(IdRequest request) throws BusinessException {
		MerchantEntity entity = getMerchantById(request.getId());
		MerchantFlagsResponse response = new MerchantFlagsResponse();
		response.setIsActive(entity.getIsActive());
		response.setCanEdit(entity.getCanEdit());
		return response;
	}

	public void setActiveAndEditFlags(MerchantFlagsRequest request) throws BusinessException {
		MerchantEntity entity = getMerchantById(request.getId());
		entity.setIsActive(request.getIsActive());
		entity.setCanEdit(request.getCanEdit());
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}
	
	@Transactional
	public void saveNewMerchant(NewMerchantRequest request) throws BusinessException {
		LocalityEntity locality = (LocalityEntity) sessionFactory.getCurrentSession().get(LocalityEntity.class, request.getLocalityId());
		if (locality == null) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		MerchantEntity newMerchant = mapper.mapNewMerchant(request, locality);
		try {
			sessionFactory.getCurrentSession().save(newMerchant);
		} catch (Exception e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		
	}

	private MerchantEntity getMerchantById(Integer id) throws BusinessException {
		MerchantEntity merchant = (MerchantEntity) sessionFactory.getCurrentSession().get(MerchantEntity.class, id);
		if (merchant == null) {
			throw new BusinessException(MerchantErrorCodeType.MERCHANT_DETAILS_NOT_FOUND);
		}
		return merchant;
	}

	private void canDishesBeUpdated(MerchantEntity entity) throws BusinessException {
		if (!entity.getCanEdit()) {
			throw new BusinessException(DishErrorCodeType.CAN_NOT_ADD_EDIT_DISH);
		}
	}

	/**
	 * Generates unique nameId cuisine and suggestion names
	 * 
	 * @param dto
	 * @param cal
	 * @return
	 */
	private String generateNameId(String name) {
		String[] nameArr = name.split(" ");
		StringBuilder nameId = new StringBuilder();
		int i;
		for (i = 0; i < nameArr.length - 1; i++) {
			nameId.append(nameArr[i].toLowerCase().trim() + "-");
		}
		nameId.append(nameArr[i].toLowerCase().trim());
		return nameId.toString();
	}
}
