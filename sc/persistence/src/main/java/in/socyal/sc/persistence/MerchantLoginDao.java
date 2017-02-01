package in.socyal.sc.persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
 
	public void saveRegistrationIdForMerchant(Integer merchantId, String userId, String registrationId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MerchantLoginEntity.class);
		criteria.add(Restrictions.eq("merchant.id", merchantId));
		criteria.add(Restrictions.eq("userId", userId));
		MerchantLoginEntity merchantLogin = (MerchantLoginEntity) criteria.uniqueResult();
		merchantLogin.setRegistrationId(registrationId);
		merchantLogin.setUpdatedDateTime(clock.cal());
		session.update(merchantLogin);
	}
}
