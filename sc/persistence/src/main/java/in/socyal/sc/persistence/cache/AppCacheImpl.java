package in.socyal.sc.persistence.cache;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@ApplicationScoped
@Component
public class AppCacheImpl implements AppCache {
	private static final Logger LOG = Logger.getLogger(AppCacheImpl.class);
	static final String FILE_NAME = "infinispan.xml";

	EmbeddedCacheManager cacheManager;

	@PostConstruct
	synchronized void init() {
		try {
			this.cacheManager = new DefaultCacheManager(FILE_NAME, true);
			Set<String> names = this.cacheManager.getCacheNames();
			this.cacheManager.startCaches(names.toArray(new String[names.size()]));
		} catch (Exception e) {
			LOG.error("Failed to load infinispan config file" + FILE_NAME, e);
		}
	}

	@PreDestroy
	synchronized void stop() {
		if (this.cacheManager != null) {
			this.cacheManager.stop();
		}
	}

	public <K, V> Cache<K, V> getCache(String name) {
		if (this.cacheManager == null) {
			throw new IllegalStateException("Cache manager is null");
		} else if (!this.cacheManager.cacheExists(name)) {
			throw new IllegalStateException("Cache not found" + name);
		}

		return new InfinispanCache<>(this.cacheManager.<K, V> getCache(name));
	}

	public <K, V> Cache<K, V> getCache(Class<?> clazz) {
		return getCache(getCanonicalName(clazz));
	}

	String getCanonicalName(Class<?> clazz) {
		String canonicalName = clazz.getCanonicalName();
		if (canonicalName == null) {
			throw new IllegalArgumentException("Class doesnot have a canonical name :" + clazz.getName());
		}

		return canonicalName;
	}

	String getName(InjectionPoint ip) {
		CacheInstance annotation = ip.getAnnotated().getAnnotation(CacheInstance.class);
		if (annotation != null) {
			String name = annotation.name();
			if (!name.isEmpty()) {
				return name;
			}

			Class<?> clazz = annotation.value();
			if (!clazz.equals(CacheInstance.class)) {
				return getCanonicalName(clazz);
			}
		}

		return ip.getMember().getName();
	}
	
	@Produces @CacheInstance
	<K, V> Cache<K, V> getCache(InjectionPoint ip) {
		try {
			return getCache(getName(ip));
		} catch (IllegalArgumentException | IllegalStateException e) {
			LOG.error("Failed to get cache instance : " , e);
			throw e;
		}
	}
}
