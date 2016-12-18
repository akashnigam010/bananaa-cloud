package in.socyal.sc.persistence;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import in.socyal.sc.persistence.interceptor.LoggerInterceptor;
 
@Configuration
@EnableTransactionManagement
@ComponentScan({ "in.socyal.sc" })
@EnableWebMvc
@PropertySource(value = {"classpath:application.properties"})
public class SpringConfigurer extends WebMvcConfigurerAdapter {
	private static final Logger LOG = Logger.getLogger(SpringConfigurer.class);
	
    @Autowired
    private Environment environment;
 
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] {"in.socyal.sc"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
     
    //Datasource configuration using JNDI Lookup
    @Bean
    public DataSource dataSource() {
    	try {
	    	final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
	    	dsLookup.setResourceRef(Boolean.TRUE);
	    	DataSource dataSource = dsLookup.getDataSource("java:jboss/datasources/MySqlDS");
	        return dataSource;
    	} catch (DataSourceLookupFailureException exception) {
    		LOG.warn("MySQL JNDI binding failure, configuring data source using driver manager ", exception);
    		 //DataSource configuration using application.properties
    		DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
            dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
            dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
            dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
            return dataSource;
    	}
    }
     
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;        
    }
     
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
    }
}
