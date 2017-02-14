package in.socyal.sc.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.merchant.business.dto.MerchantLoginDto;
import in.socyal.sc.date.util.Clock;
import in.socyal.sc.persistence.entity.MerchantLoginEntity;
import in.socyal.sc.persistence.mapper.MerchantLoginDaoMapper;

@Repository
public class MerchantLoginDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired MerchantLoginDaoMapper mapper;
	@Autowired Clock clock;
 
    public MerchantLoginDao() {
    }
     
    public MerchantLoginDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    public MerchantLoginDto validateBusinessUser(String username, String password) {
    	MerchantLoginDto merchantLoginDto = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MerchantLoginEntity.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		MerchantLoginEntity merchantLoginEntity = (MerchantLoginEntity) criteria.uniqueResult();
		if (merchantLoginEntity != null) {
			merchantLoginDto = new MerchantLoginDto();
			mapper.map(merchantLoginEntity, merchantLoginDto);
		}
		return merchantLoginDto;
	}
    
	public void saveRegistrationIdForMerchant(Integer merchantId, Integer deviceId, String registrationId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MerchantLoginEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("deviceId", deviceId));
		MerchantLoginEntity merchantLogin = (MerchantLoginEntity) criteria.uniqueResult();
		merchantLogin.setRegistrationId(registrationId);
		merchantLogin.setUpdatedDateTime(clock.cal());
		session.update(merchantLogin);
	}
	
	public Boolean isMerchantLoginDetailsPresent(Integer merchantId, Integer deviceId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MerchantLoginEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("deviceId", deviceId));
		MerchantLoginEntity merchantLogin = (MerchantLoginEntity) criteria.uniqueResult();
		return (merchantLogin != null) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRegistrationIdsForMerchant(Integer merchantId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MerchantLoginEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		List<MerchantLoginEntity> merchantEntities = criteria.list();
		return mapper.mapMerchantRegistrationIds(merchantEntities);
	}
}
