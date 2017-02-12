package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinFilterCriteria;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.reward.request.Reward;
import in.socyal.sc.api.reward.request.SubmitRewardsRequest;
import in.socyal.sc.api.rewards.dto.RewardsDto;
import in.socyal.sc.api.type.RewardStatusType;
import in.socyal.sc.api.type.error.CheckinErrorCodeType;
import in.socyal.sc.api.type.error.RewardErrorCodeType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.RewardsEntity;
import in.socyal.sc.persistence.mapper.CheckinDaoMapper;
import in.socyal.sc.persistence.mapper.RewardsDaoMapper;

@Repository
public class RewardsDao {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	RewardsDaoMapper mapper;
	@Autowired
	CheckinDaoMapper checkinMapper;
	@Autowired
	Clock clock;

	public RewardsDao() {
	}

	public RewardsDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CheckinDto saveRewardStatus(Integer checkinId, RewardStatusType newStatus, CheckinFilterCriteria filter) {
		CheckinEntity checkinEntity = (CheckinEntity) getSession().get(CheckinEntity.class, checkinId);
		if (checkinEntity == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		checkinEntity.setRewardStatus(newStatus);
		checkinEntity.setUpdatedDateTime(clock.cal());
		getSession().update(checkinEntity);
		CheckinDto checkinDto = new CheckinDto();
		checkinMapper.mapToCheckinDto(checkinEntity, checkinDto, filter);
		return checkinDto;
	}

	public CheckinDto saveReward(SubmitRewardsRequest request, CheckinFilterCriteria filter) throws BusinessException {
		CheckinEntity checkinEntity = (CheckinEntity) getSession().get(CheckinEntity.class, request.getCheckinId());
		if (checkinEntity == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}

		checkRewardStatus(checkinEntity);
		checkinEntity.setRewardStatus(RewardStatusType.GIVEN);
		checkinEntity.setRewardMessage(createRewardMessage(request));
		checkinEntity.setUpdatedDateTime(clock.cal());
		getSession().update(checkinEntity);
		CheckinDto checkinDto = new CheckinDto();
		checkinMapper.mapToCheckinDto(checkinEntity, checkinDto, filter);
		return checkinDto;
	}

	public List<RewardsDto> fetchRewardsList(Integer merchantId) {
		List<RewardsDto> rewards = Collections.emptyList();
		Criteria criteria = getSession().createCriteria(RewardsEntity.class);
		criteria.add(Restrictions.eq(RewardsEntity.MERCHANT_ID, merchantId));
		@SuppressWarnings("unchecked")
		List<RewardsEntity> result = criteria.list();
		if (result.size() > 0) {
			rewards = new ArrayList<>();
			mapper.map(result, rewards);
		}
		return rewards;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private String createRewardMessage(SubmitRewardsRequest request) {
		String rewardMessage = "";
		List<RewardsEntity> rewardsEntities = fetchRewardEntites(request.getRewards());
		if (rewardsEntities.size() == 0 && request.getDiscount() != null) {
			rewardMessage += "Discount worth Rs. " + request.getDiscount();
		} else if (rewardsEntities.size() != 0 && request.getDiscount() == null) {
			boolean isMorethanOne = false;
			int i = 0;
			for (i = 0; i < rewardsEntities.size() - 1; i++) {
				rewardMessage += rewardsEntities.get(i).getReward();
				if (i < rewardsEntities.size() - 2) {
					rewardMessage += ", ";
				} else {
					rewardMessage += " ";
				}
				isMorethanOne = true;
			}
			if (isMorethanOne) {
				rewardMessage += "and ";
			}
			rewardMessage += rewardsEntities.get(i).getReward();
		} else if (rewardsEntities.size() != 0 && request.getDiscount() != null) {
			int i = 0;
			for (i = 0; i < rewardsEntities.size() - 1; i++) {
				rewardMessage += rewardsEntities.get(i).getReward();
				if (i < rewardsEntities.size() - 1) {
					rewardMessage += ", ";
				} else {
					rewardMessage += " ";
				}
			}
			rewardMessage += rewardsEntities.get(i).getReward();
			rewardMessage += " and Discount worth Rs. " + request.getDiscount();
		}

		return rewardMessage;
	}

	@SuppressWarnings("unchecked")
	private List<RewardsEntity> fetchRewardEntites(List<Reward> rewardDtos) {
		List<RewardsEntity> entities = new ArrayList<>();
		if (rewardDtos.size() > 0) {
			Criteria criteria = getSession().createCriteria(RewardsEntity.class);
			criteria.add(Restrictions.in("id", getRewardIdCollection(rewardDtos)));
			entities = criteria.list();
		}
		return entities;
	}

	private List<Integer> getRewardIdCollection(List<Reward> rewardDtos) {
		List<Integer> idCollection = new ArrayList<>();
		for (Reward rewardDto : rewardDtos) {
			idCollection.add(rewardDto.getId());
		}
		return idCollection;
	}

	private void checkRewardStatus(CheckinEntity checkin) throws BusinessException {
		if (checkin.getRewardStatus() == RewardStatusType.GIVEN || checkin.getRewardStatus() == RewardStatusType.SEEN) {
			throw new BusinessException(RewardErrorCodeType.REWARD_ALREADY_GIVEN);
		}
	}
}
