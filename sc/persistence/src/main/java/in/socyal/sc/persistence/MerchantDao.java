package in.socyal.sc.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.api.merchant.dto.GetMerchantListRequestDto;
import in.socyal.sc.api.merchant.dto.MerchantDto;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.mapper.MerchantDaoMapper;

@Repository
public class MerchantDao {
	@Autowired SessionFactory sessionFactory;
	@Autowired MerchantDaoMapper mapper;
 
    public MerchantDao() {
    }
     
    public MerchantDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Transactional
    public List<MerchantDto> getMerchants(GetMerchantListRequestDto request) {
    	List<MerchantDto> merchantDtos = null;
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MerchantEntity.class);
    	@SuppressWarnings("unchecked")
		List<MerchantEntity> merchants = (List<MerchantEntity>) criteria.list();
    	if (merchants != null && !merchants.isEmpty()) {
    		merchantDtos = new ArrayList<>();
    		mapper.map(merchants, merchantDtos);
		}
    	
    	return merchantDtos;
    	
    }
    
    @Transactional
    public MerchantDto getMerchantDetails(Integer id) throws BusinessException {
    	MerchantDto dto = null;
    	MerchantEntity entity = (MerchantEntity) sessionFactory.getCurrentSession().get(MerchantEntity.class, id);
    	if (entity != null) {
    		dto = new MerchantDto();
    		mapper.map(entity, dto);
    	}
    	
    	return dto;
    }
    
    @Transactional
    public void saveMerchantDetails(MerchantDto merchantDto) {
    	 MerchantEntity entity = new MerchantEntity();
    	 mapper.map(merchantDto, entity);
         //Delete
         /*entity.setName("Heart Cup Cafe");
         entity.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
         entity.setRating(3.9);
         entity.setCloseTime(23.00);
         entity.setOpenTime(11.00);
         AddressEntity address = new AddressEntity();
         address.setAddressLine1("Beside TCS");
         address.setAddressLine2("Kondapur, Hyderabad");
         address.setCity("Hyderabad");
         address.setCountry("India");
         address.setLatitude(17.459838);
         address.setLongitude(78.368727);
         address.setState("Telangana");
         address.setZip("500084");
         entity.setAddress(address);
         ContactEntity contact = new ContactEntity();
         contact.setEmail("heartcupcafe@gmail.com");
         contact.setMobile("8888888888");
         contact.setTelephone("04046464646");
         entity.setContact(contact);
         entity.setCheckins(10);
         entity.setCuisines("Indian, Chinese, Thai, Malaysian, Continental, Lebanese, Afghani");*/
         //Delete
         sessionFactory.getCurrentSession().save(entity);
    }
}
