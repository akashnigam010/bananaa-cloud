package in.socyal.sc.persistence;

import static in.socyal.sc.api.type.FeedbackStatusType.ASKED;
import static in.socyal.sc.api.type.FeedbackStatusType.NOT_ASKED;
import static in.socyal.sc.api.type.FeedbackStatusType.RECEIVED;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.checkin.dto.CheckinDto;
import in.socyal.sc.api.checkin.dto.CheckinFilterCriteria;
import in.socyal.sc.api.feedback.request.SubmitFeedbackRequest;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.FeedbackStatusType;
import in.socyal.sc.api.type.error.CheckinErrorCodeType;
import in.socyal.sc.api.type.error.FeedbackErrorCodeType;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.CheckinEntity;
import in.socyal.sc.persistence.entity.FeedbackEntity;
import in.socyal.sc.persistence.mapper.CheckinDaoMapper;
import in.socyal.sc.persistence.mapper.FeedbackDaoMapper;

@Repository
public class FeedbackDao {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	FeedbackDaoMapper mapper;
	@Autowired
	CheckinDaoMapper checkinMapper;
	@Autowired
	Clock clock;

	public FeedbackDao() {
	}

	public FeedbackDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CheckinDto saveFeedbackStatus(Integer checkinId, FeedbackStatusType newStatus, CheckinFilterCriteria filter)
			throws BusinessException {
		CheckinEntity checkinEntity = (CheckinEntity) getSession().get(CheckinEntity.class, checkinId);
		if (checkinEntity == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		FeedbackEntity currentFeedback = checkinEntity.getFeedback();
		checkFeedbackStatus(currentFeedback, newStatus);
		currentFeedback.setStatus(newStatus);
		checkinEntity.setFeedback(currentFeedback);
		getSession().save(checkinEntity);
		CheckinDto checkinDto = new CheckinDto();
		checkinMapper.mapToCheckinDto(checkinEntity, checkinDto, filter);
		return checkinDto;
	}

	public CheckinDto saveFeedback(Integer checkinId, SubmitFeedbackRequest request, CheckinFilterCriteria filter)
			throws BusinessException {
		CheckinEntity checkinEntity = (CheckinEntity) getSession().get(CheckinEntity.class, checkinId);
		if (checkinEntity == null) {
			throw new BusinessException(CheckinErrorCodeType.CHECKIN_ID_NOT_FOUND);
		}
		FeedbackEntity currentFeedback = checkinEntity.getFeedback();
		currentFeedback.setStatus(RECEIVED);
		currentFeedback.setFoodRating(Double.valueOf(request.getFoodRating()));
		currentFeedback.setAmbienceRating(Double.valueOf(request.getAmbienceRating()));
		currentFeedback.setServiceRating(Double.valueOf(request.getServiceRating()));
		checkinEntity.setFeedback(currentFeedback);
		getSession().save(checkinEntity);
		CheckinDto checkinDto = new CheckinDto();
		checkinMapper.mapToCheckinDto(checkinEntity, checkinDto, filter);
		return checkinDto;
	}

	private void checkFeedbackStatus(FeedbackEntity currentFeedback, FeedbackStatusType newStatus)
			throws BusinessException {
		if (currentFeedback.getStatus() == ASKED && newStatus == ASKED) {
			throw new BusinessException(FeedbackErrorCodeType.FEEDBACK_ALREADY_ASKED);
		}
		if (currentFeedback.getStatus() == NOT_ASKED && newStatus == NOT_ASKED) {
			throw new BusinessException(FeedbackErrorCodeType.FEEDBACK_ALREADY_CANCELLED);
		}
		if (currentFeedback.getStatus() == RECEIVED) {
			if (newStatus == ASKED || newStatus == NOT_ASKED) {
				throw new BusinessException(FeedbackErrorCodeType.FEEDBACK_ALREADY_RECEIVED);
			}
		}
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
