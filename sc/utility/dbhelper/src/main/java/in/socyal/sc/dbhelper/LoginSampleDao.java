package in.socyal.sc.dbhelper;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginSampleDao {
    @Autowired
    private SessionFactory sessionFactory;
 
    public LoginSampleDao() {
    }
     
    public LoginSampleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Transactional
    public void save(String name) {
        LoginEntity entity = new LoginEntity();
        entity.setName(name);
        sessionFactory.getCurrentSession().save(entity);
    }
}

