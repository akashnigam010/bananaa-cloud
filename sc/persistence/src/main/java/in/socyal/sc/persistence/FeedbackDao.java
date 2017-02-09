package in.socyal.sc.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.feedback.dto.FeedbackDto;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.FeedbackEntity;
import in.socyal.sc.persistence.mapper.FeedbackDaoMapper;

@Repository
public class FeedbackDao {
	private static final Logger LOG = Logger.getLogger(FeedbackDao.class);
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	FeedbackDaoMapper mapper;
	@Autowired
	Clock clock;

	public FeedbackDao() {
	}

	public FeedbackDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
		FeedbackEntity entity = new FeedbackEntity();
		Session session = sessionFactory.getCurrentSession();
		mapper.map(feedbackDto, entity);
		session.save(entity);
		feedbackDto.setId(entity.getId());
		return feedbackDto;
	}
}
