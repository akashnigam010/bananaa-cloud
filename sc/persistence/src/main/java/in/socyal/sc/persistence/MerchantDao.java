package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.api.merchant.dto.MerchantFilterCriteria;
import in.socyal.sc.api.type.MerchantListSortType;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.mapper.MerchantDaoMapper;

@Repository
public class MerchantDao {
	private static final String NAME = "name";
	@Autowired SessionFactory sessionFactory;
	@Autowired MerchantDaoMapper mapper;
	private static final Integer RESULTS_PER_PAGE = 10;
 
    public MerchantDao() {
    }
     
    public MerchantDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    /**
     * This method is used to fetch merchant list using criteria
     * sorting logic is happening on PROMOTION value
     * @param request
     * @return
     */
    public List<MerchantDto> getMerchantsByRatingOrPromotion(GetMerchantListRequestDto request, 
    		MerchantListSortType sortType, MerchantFilterCriteria filter) {
    	List<MerchantDto> merchantDtos = null;
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
    	int firstResult = ((request.getPage() - 1) * RESULTS_PER_PAGE);
    	criteria.addOrder(Order.desc(sortType.getCode()));
    	criteria.setFirstResult(firstResult);
    	criteria.setMaxResults(RESULTS_PER_PAGE);
    	@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
    	if (merchants != null && !merchants.isEmpty()) {
    		merchantDtos = new ArrayList<>();
    		mapper.map(merchants, merchantDtos, filter);
		}
    	
    	return merchantDtos;
    }
    
    /**
     * This method is used to fetch merchant list using native sql query 
     * sorting logic is happening based on distance
     * @param request
     * @return
     */
    public List<MerchantDto> getMerchantsByDistance(GetMerchantListRequestDto request, MerchantFilterCriteria filter) {
    	List<MerchantDto> merchantDtos = null;
    	SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sortMerchantsByDistanceQuery());
    	query.addEntity(MerchantEntity.class);
    	query.setDouble("latitude", request.getLatitude());
    	query.setDouble("longitude", request.getLongitude());
    	int firstResult = ((request.getPage() - 1) * RESULTS_PER_PAGE);
    	query.setFirstResult(firstResult);
    	query.setMaxResults(RESULTS_PER_PAGE);
    	@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) query.list();
    	if (merchants != null && !merchants.isEmpty()) {
    		merchantDtos = new ArrayList<>();
    		mapper.map(merchants, merchantDtos, filter);
		}
    	return merchantDtos;
    }
    
    public MerchantDto getMerchantDetails(Integer id, MerchantFilterCriteria filter) throws BusinessException {
    	MerchantDto dto = null;
    	MerchantEntity entity = (MerchantEntity) sessionFactory.getCurrentSession().get(MerchantEntity.class, id);
    	if (entity != null) {
    		dto = new MerchantDto();
    		mapper.map(entity, dto, filter);
    	}
    	
    	return dto;
    }
    
    public List<MerchantDto> searchMerchant(String restaurantName, MerchantFilterCriteria filter) throws BusinessException {
    	List<MerchantDto> merchantDtos = null;
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
    
    public void saveMerchantDetails(MerchantDto merchantDto) {
   	 MerchantEntity entity = new MerchantEntity();
        sessionFactory.getCurrentSession().save(entity);
   }
    
    public void updateMerchantCheckinCountDetails(Integer merchantId) {
    	Session session = sessionFactory.getCurrentSession();
    	MerchantEntity entity = (MerchantEntity) session.get(MerchantEntity.class, merchantId);
    	if (entity != null) {
    		Integer checkinCount = entity.getCheckins();
    		entity.setCheckins(checkinCount + 1);
    		session.saveOrUpdate(entity);
    	}
     }
    
    private String sortMerchantsByDistanceQuery() {
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT * ");
    	query.append("FROM Socyal.MERCHANT m INNER JOIN Socyal.ADDRESS a ");
    	query.append("ON m.ADDRESS_ID = a.ID ");
    	query.append("ORDER BY Socyal.DISTANCE_BETWEEN_COORDINATES(:latitude, :longitude, a.LATITUDE, a.LONGITUDE) ASC");
    	return query.toString();
    }
}
