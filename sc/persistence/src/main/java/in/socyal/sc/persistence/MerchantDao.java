package in.socyal.sc.persistence;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.socyal.sc.persistence.entity.AddressEntity;
import in.socyal.sc.persistence.entity.ContactEntity;
import in.socyal.sc.persistence.entity.MerchantEntity;
import in.socyal.sc.persistence.entity.TimingEntity;

@Repository
public class MerchantDao {
	@Autowired
    private SessionFactory sessionFactory;
 
    public MerchantDao() {
    }
     
    public MerchantDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Transactional
    public void getMerchants() {
    	MerchantEntity entity = (MerchantEntity) sessionFactory.getCurrentSession().get(MerchantEntity.class, 1);
    }
    
    @Transactional
    public void saveMerchantDetails() {
    	 MerchantEntity entity = new MerchantEntity();
         //Delete
         entity.setName("Heart Cup Cafe");
         entity.setImageUrl("http://www.whitebay.in/images/heartcupcafecoffee.png");
         entity.setRating(3.9);
         AddressEntity address = new AddressEntity();
         address.setAddressLine1("Beside TCS");
         address.setAddressLine2("Kondapur, Hyderabad");
         address.setCity("Hyderabad");
         address.setCountry("India");
         address.setLatitude(new BigDecimal(17.459838));
         address.setLongitude(new BigDecimal(78.368727));
         address.setState("Telangana");
         address.setZip("500084");
         entity.setAddress(address);
         ContactEntity contact = new ContactEntity();
         contact.setEmail("heartcupcafe@gmail.com");
         contact.setMobile("8888888888");
         contact.setTelephone("04046464646");
         entity.setContact(contact);
         TimingEntity timing = new TimingEntity();
         timing.setCloseTime(23.00);
         timing.setOpenTime(11.00);
         entity.setTiming(timing);
         //Delete
         sessionFactory.getCurrentSession().save(entity);
    }
}
