package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.checkin.dto.CheckinDetailsDto;
import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.type.CheckinStatusType;
import in.socyal.sc.date.util.Clock;
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
		mapper.map(checkinDetails, entity);
		return (Integer) sessionFactory.getCurrentSession().save(entity);
	}

	/**
	 * Use method <link>getUserCheckinsCountForAMerchant</link> to get checkin counts<br>
	 * This method gets APPROVED checkins for a merchant, mapped with all data fields
	 * 
	 * @param userId
	 * @param merchantId
	 * @return
	 */
	public List<CheckinDto> getUserCheckinsForAMerchant(Integer userId, Integer merchantId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("status", CheckinStatusType.APPROVED));
		@SuppressWarnings("unchecked")
		List<CheckinEntity> result = criteria.list();
		List<CheckinDto> response = Collections.emptyList();
		if (result.size() > 0) {
			response = new ArrayList<>();
			mapper.map(result, response);
		}
		return response;
	}

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
	public List<CheckinDto> getMerchantCheckins(Integer merchantId, Integer page) {
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
			mapper.map(result, response);
		}
		return response;
	}

	public void cancelCheckin(Integer checkinId) {
		CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
		if (checkin != null) {
			checkin.setStatus(CheckinStatusType.CANCELLED);
			checkin.setUpdatedDateTime(clock.cal());
			sessionFactory.getCurrentSession().update(checkin);
		} else {
			LOG.error("Checkin entity not found for checkinID:" + checkinId);
		}
	}

	public CheckinDto getCheckin(Integer checkinId) {
		CheckinDto checkinDto = null;
		CheckinEntity checkin = (CheckinEntity) sessionFactory.getCurrentSession().get(CheckinEntity.class, checkinId);
		if (checkin != null) {
			checkinDto = new CheckinDto();
			mapper.map(checkin, checkinDto);
		}
		return checkinDto;
	}
	
	public List<CheckinDto> getUserCheckins(Integer userId, Integer page) {
		List<CheckinDto> checkinDtos = Collections.emptyList();
		int firstResult = ((page - 1) * RESULTS_PER_PAGE);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CheckinEntity.class);
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
				mapper.map(entity, dto);
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
}
