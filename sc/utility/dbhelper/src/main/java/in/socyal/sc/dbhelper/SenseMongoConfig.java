package in.socyal.sc.dbhelper;

import java.net.UnknownHostException;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

@Configuration
@PropertySource(value = "classpath:db.properties", ignoreResourceNotFound = true)
public class SenseMongoConfig {
	@Value("${DB_USERNAME}")
	String DB_USERNAME;

	@Value("${DB_PASSWORD}")
	String DB_PASSWORD;

	@Value("${DB_NAME}")
	String DB_NAME;

	@Value("${DB_HOST}")
	String DB_HOST;

	@Value("${DB_PORT}")
	String DB_PORT;

	@Value("${DB_PROTOCOL}")
	String DB_PROTOCOL;

	@Value("${DB_AUTH_MECHANISM}")
	String DB_AUTH_MECHANISM;

	@Bean
	public MongoClientURI senseMongoUri() {
		MongoClientURI uri = new MongoClientURI(formSenseMongoUri());
		return uri;
	}

	@Bean
	public MongoDbFactory senseDbFactory() throws UnknownHostException {
		MongoDbFactory senseMongoDbFactory = new SimpleMongoDbFactory(senseMongoUri());
		return senseMongoDbFactory;
	}

	@Bean
	@ApplicationScoped
	public MongoTemplate senseMongoTemplate() throws UnknownHostException {
		MongoTemplate senseMongoTemplate = new MongoTemplate(senseDbFactory());
		return senseMongoTemplate;
	}

	private String formSenseMongoUri() {
		StringBuilder mongoUri = new StringBuilder();
		mongoUri.append(DB_PROTOCOL);
		mongoUri.append("://");
		if (StringUtils.isNotEmpty(DB_USERNAME) && StringUtils.isNotEmpty(DB_PASSWORD)) {
			mongoUri.append(DB_USERNAME);
			mongoUri.append(":");
			mongoUri.append(DB_PASSWORD);
			mongoUri.append("@");
		}
		mongoUri.append(DB_HOST);
		mongoUri.append(":");
		mongoUri.append(DB_PORT);
		mongoUri.append("/");
		mongoUri.append(DB_NAME);
		mongoUri.append("?");
		mongoUri.append("authMechanism=");
		mongoUri.append(DB_AUTH_MECHANISM);
		return mongoUri.toString();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
