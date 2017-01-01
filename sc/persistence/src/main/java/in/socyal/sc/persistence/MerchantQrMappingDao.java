package in.socyal.sc.persistence;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.qr.dto.MerchantQrMappingDto;
import in.socyal.sc.persistence.entity.MerchantQrMappingEntity;
import in.socyal.sc.persistence.mapper.MerchantQrMappingMapper;

@Repository
public class MerchantQrMappingDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired MerchantQrMappingMapper mapper;
 
    public MerchantQrMappingDao() {
    }
     
    public MerchantQrMappingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    public MerchantQrMappingDto getMerchantQrMapping(String qrCode) {
    	MerchantQrMappingDto dto = null;
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantQrMappingEntity.class);
    	criteria.add(Restrictions.eq("qrCode", qrCode));
    	MerchantQrMappingEntity entity = (MerchantQrMappingEntity) criteria.uniqueResult();
    	if (entity != null) {
    		dto = new MerchantQrMappingDto();
    		mapper.map(entity, dto);
    	}
    	return dto;
    }
}
