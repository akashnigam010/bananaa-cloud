package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.merchant.dto.TrendingMerchantResultDto;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.RecommendationEntity;
import in.socyal.sc.persistence.entity.TrendingMerchantResultEntity;
import in.socyal.sc.persistence.mapper.RecommendationDaoMapper;

@Repository
public class RecommendationDao {
	private static final Logger LOG = Logger.getLogger(RecommendationDao.class);
	private static final Integer RESULTS_PER_PAGE = 5;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	RecommendationDaoMapper mapper;
	@Autowired
	Clock clock;

	public RecommendationDao() {
	}

	public RecommendationDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<TrendingMerchantResultDto> getTrendingMerchants() {
		List<TrendingMerchantResultDto> response = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RecommendationEntity.class);
		criteria.createAlias("dish", "d");
		criteria.createAlias("d.merchant", "m");
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("m.id").as("recommendations"));
		projList.add(Projections.groupProperty("m.id").as("merchantId"));
		projList.add(Projections.property("d.merchant").as("merchant"));
		criteria.setProjection(projList);
		criteria.addOrder(Order.desc("recommendations"));
		criteria.setResultTransformer(Transformers.aliasToBean(TrendingMerchantResultEntity.class));
		List<TrendingMerchantResultEntity> result = (List<TrendingMerchantResultEntity>) criteria.list();
		mapper.map(result, response);
		return response;
	}
	
    private String sortMerchantsByMaxRcmdnQuery() {
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT COUNT(*) AS RECOMMENDATIONS, r.MERCHANT_ID ");
    	query.append("FROM bna.RECOMMENDATION r ");
    	query.append("GROUP BY r.MERCHANT_ID ");
    	query.append("ORDER BY RECOMMENDATIONS DESC");
    	return query.toString();
    }
}
