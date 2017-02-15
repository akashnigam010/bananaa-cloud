package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinFilterCriteria;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.api.type.error.CheckinErrorCodeType;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.date.util.DayUtil;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.mapper.CheckinDaoMapper;

@Repository
public class CheckinDao {
	private static final Logger LOG = Logger.getLogger(CheckinDao.class);
	private static final Integer RESULTS_PER_PAGE = 10;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	CheckinDaoMapper mapper;
	@Autowired
	Clock clock;

	public CheckinDao() {
	}

	public CheckinDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Integer confirmCheckin(CheckinDetailsDto checkinDetails) {
		CheckinEntity entity = new CheckinEntity();
		mapper.mapToCheckinEntity(checkinDetails, entity);
		return (Integer) sessionFactory.getCurrentSession().save(entity);
	}

	/**
	 * Use method <link>getUserCheckinsCountForAMerchant</link> to get checkin
	 * counts<br>
	 * This method gets APPROVED checkins for a merchant, mapped with all data
	 * fields
	 * 
	 * @param userId
	 * @param merchantId
	 * @return
	 */
	// public List<CheckinDto> getUserCheckinsForAMerchant(Integer userId,
	// Integer merchantId) {
	// Criteria criteria =
	// sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
	// criteria.add(Restrictions.eq("user.id", userId));
	// criteria.add(Restrictions.eq("merchant.id", merchantId));
	// criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
	// @SuppressWarnings("unchecked")
	// List<CheckinEntity> result = criteria.list();
	// List<CheckinDto> response = Collections.emptyList();
	// if (result.size() > 0) {
	// response = new ArrayList<>();
	// //mapper.map(result, response);
	// }
	// return response;
	// }

	/**
	 * This method gets APPROVED checkins count for a merchant for the current
	 * logged in USER
	 * 
	 * @param userId
	 * @param merchantId
	 * @return
	 */
	public Integer getUserCheckinsCountForAMerchant(Integer userId, Integer merchantId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		@SuppressWarnings("unchecked")
		List<CheckinEntity> checkins = criteria.list();
		return checkins == null ? 0 : checkins.size();
	}

	/**
	 * This method gets a particular MERCHANT checkins
	 * 
	 * @param merchantId
	 * @param page
	 * @return
	 */
	public List<CheckinDto> getMerchantCheckins(Integer merchantId, Integer page, CheckinFilterCriteria filter) {
		List<CheckinDto> response = Collections.emptyList();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		criteria.addOrder(Order.desc("checkinDateTime"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<CheckinEntity> result = (List<CheckinEntity>) criteria.list();
		if (result.size() > 0) {
			response = new ArrayList<>();
			mapper.map(result, response, filter);
		}
		return response;
	}

	public void cancelCheckin(Integer checkinId) throws BusinessException {
		CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
		if (checkin == null) {
			LOG.error("Checkin entity not found for checkinID:" + checkinId);
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		} else {
			if (CheckinStatusType.APPROVED == checkin.getStatus()) {
				LOG.error("Checkin is already aprpoved for checkinID:" + checkinId);
				throw new BusinessException(CheckinErrorCodeType.USER_CHECKIN_ALREADY_APPROVED);
			} else if (CheckinStatusType.USER_CANCELLED == checkin.getStatus() 
					|| CheckinStatusType.MERCHANT_CANCELLED == checkin.getStatus()) {
				LOG.error("Checkin is already cancelled for checkinID:" + checkinId);
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED);
			}
			checkin.setStatus(CheckinStatusType.USER_CANCELLED);
			checkin.setUpdatedDateTime(clock.cal());
			sessionFactory.getCurrentSession().update(checkin);
		}
	}

	public CheckinDto getCheckin(Integer checkinId, CheckinFilterCriteria filter) {
		CheckinDto checkinDto = null;
		CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
		if (checkin != null) {
			checkinDto = new CheckinDto();
			mapper.mapToCheckinDto(checkin, checkinDto, filter);
		}
		return checkinDto;
	}

	/**
	 * This method is used to fetch current user checkins along with the
	 * following users' checkins
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<CheckinDto> getUserCheckins(List<Integer> userIds, Integer page, CheckinFilterCriteria filter) {
		List<CheckinDto> checkinDtos = Collections.emptyList();
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.in("user.id", userIds));
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		criteria.addOrder(Order.desc("checkinDateTime"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
		if (result.size() > 0) {
			checkinDtos = new ArrayList<>();
			for (CheckinEntity entity : result) {
				CheckinDto dto = new CheckinDto();
				mapper.mapToCheckinDto(entity, dto, filter);
				checkinDtos.add(dto);
			}
		}
		return checkinDtos;
	}

	public Integer getUserCheckinCount(Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
		return result.size();
	}

	public Integer getLikeCountForACheckin(Integer checkinId) {
		Integer likeCount = 0;
		CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
		if (checkin != null) {
			likeCount = checkin.getLikes().size();
		}
		return likeCount;
	}

	/**
	 * This method gets all the latest checkins happening around
	 * 
	 * @param page
	 * @return
	 */
	public List<CheckinDto> getAroundMeFeeds(Integer userId, Integer page, CheckinFilterCriteria filter) {
		List<CheckinDto> checkinDtos = Collections.emptyList();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		if (userId != null) {
			criteria.add(Restrictions.not(Restrictions.eq("user.id", userId)));
		}
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		criteria.addOrder(Order.desc("checkinDateTime"));
		@SuppressWarnings("unchecked")
		List<CheckinEntity> checkins = (List<CheckinEntity>) criteria.list();
		if (checkins != null && !checkins.isEmpty()) {
			checkinDtos = new ArrayList<>();
			for (CheckinEntity checkin : checkins) {
				CheckinDto dto = new CheckinDto();
				mapper.mapToCheckinDto(checkin, dto, filter);
				checkinDtos.add(dto);
			}
		}
		return checkinDtos;
	}

	public List<CheckinDto> getBusinessCheckins(Integer page, Calendar checkinDate, Calendar checkinNextDate,
			Integer merchantId, CheckinFilterCriteria filter) {
		List<CheckinDto> checkinDtos = Collections.emptyList();
		List<CheckinStatusType> statuses = new ArrayList<>();
		statuses.add(CheckinStatusType.APPROVED);
		statuses.add(CheckinStatusType.PENDING);
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.between("checkinDateTime", DayUtil.initialTimeOfDate(checkinDate),
				DayUtil.initialTimeOfDate(checkinNextDate)));
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.in("status", statuses));
		criteria.addOrder(Order.desc("checkinDateTime"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
		if (result.size() > 0) {
			checkinDtos = new ArrayList<>();
			for (CheckinEntity entity : result) {
				CheckinDto dto = new CheckinDto();
				mapper.mapToCheckinDto(entity, dto, filter);
				checkinDtos.add(dto);
			}
		}
		return checkinDtos;
	}
	
	public List<CheckinDto> getBusinessCheckinHistory(Integer page, Integer merchantId, Integer userId, CheckinFilterCriteria filter) {
		List<CheckinDto> checkinDtos = Collections.emptyList();
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		criteria.addOrder(Order.desc("checkinDateTime"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(RESULTS_PER_PAGE);
		@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
		if (result.size() > 0) {
			checkinDtos = new ArrayList<>();
			for (CheckinEntity entity : result) {
				CheckinDto dto = new CheckinDto();
				mapper.mapToCheckinDto(entity, dto, filter);
				checkinDtos.add(dto);
			}
		}
		return checkinDtos;
	}

	public Integer getBusinessCheckinsCountPerDay(Integer page, Calendar checkinDate, Calendar checkinNextDate,
			Integer merchantId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.between("checkinDateTime", DayUtil.initialTimeOfDate(checkinDate),
				DayUtil.initialTimeOfDate(checkinNextDate)));
		criteria.addOrder(Order.desc("checkinDateTime"));
		return criteria.list().size();
	}

	public CheckinDto businessApproveCheckin(Integer checkinId, CheckinFilterCriteria filter) throws BusinessException {
		Session session = sessionFactory.getCurrentSession();
		CheckinDto checkinDto = null;
		CheckinEntity entity = (CheckinEntity) session.get(CheckinEntity.class, checkinId);
		if (entity != null) {
			if (entity.getStatus() == CheckinStatusType.PENDING) {
				checkinDto = new CheckinDto();
				entity.setStatus(CheckinStatusType.APPROVED);
				entity.setApprovedDateTime(clock.cal());
				entity.setUpdatedDateTime(clock.cal());
				session.saveOrUpdate(entity);
				mapper.mapToCheckinDto(entity, checkinDto, filter);
			} else if (entity.getStatus() == CheckinStatusType.MERCHANT_CANCELLED) {
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED_BY_MERCHANT);
			} else if (entity.getStatus() == CheckinStatusType.USER_CANCELLED) {
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED_BY_USER);
			} else if (entity.getStatus() == CheckinStatusType.APPROVED) {
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_APPROVED);
			} else {
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
		}
		return checkinDto;
	}

	public CheckinDto businessCancelCheckin(Integer checkinId, CheckinFilterCriteria filter) throws BusinessException {
		Session session = sessionFactory.getCurrentSession();
		CheckinDto checkinDto = null;
		CheckinEntity entity = (CheckinEntity) session.get(CheckinEntity.class, checkinId);
		if (entity != null) {
			if (entity.getStatus() == CheckinStatusType.APPROVED) {
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_APPROVED);
			} else if (entity.getStatus() == CheckinStatusType.MERCHANT_CANCELLED) {
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED_BY_MERCHANT);
			} else if (entity.getStatus() == CheckinStatusType.USER_CANCELLED) {
				throw new BusinessException(CheckinErrorCodeType.CHECKIN_ALREADY_CANCELLED_BY_USER);
			} else if (entity.getStatus() == CheckinStatusType.PENDING) {
				checkinDto = new CheckinDto();
				entity.setStatus(CheckinStatusType.MERCHANT_CANCELLED);
				entity.setUpdatedDateTime(clock.cal());
				session.saveOrUpdate(entity);
				mapper.mapToCheckinDto(entity, checkinDto, filter);
			} else {
				throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
			}
		} else {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		return checkinDto;
	}

	@SuppressWarnings("unused")
	private String sortAroundMeFeedsByDistanceQuery() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT *");
		query.append("FROM Socyal.CHECKIN C ");
		query.append("INNER JOIN Socyal.MERCHANT M ON C.MERCHANT_ID = M.ID ");
		query.append("INNER JOIN Socyal.ADDRESS A ON M.ADDRESS_ID = A.ID ");
		query.append("WHERE C.STATUS = 'APPROVED' ");
		query.append("ORDER BY C.CHECKIN_DATETIME DESC, ");
		query.append("Socyal.DISTANCE_BETWEEN_COORDINATES(:latitude, :longitude, A.LATITUDE, A.LONGITUDE) ASC");
		return query.toString();
	}
}
