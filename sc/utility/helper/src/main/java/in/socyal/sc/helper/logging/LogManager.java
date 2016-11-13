package in.socyal.sc.helper.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class LogManager {
	private static final String FACTORY_KEY = "cwf.helper.logging";
	private static final String PROPERTIES = "cwf-logmanager.properties";
	private static final String INTERNAL_PROPERTIES = "META-INF/" + PROPERTIES;
	private static LoggerFactory factory;

	/*
	 * static { String name = getNameFromSystemProperty(); if (name == null) {
	 * name = getNameFromResource(LogManager.class.getClassLoader(),
	 * PROPERTIES); if (name == null) { name =
	 * getNameFromResource(Thread.currentThread().getContextClassLoader(),
	 * INTERNAL_PROPERTIES); } } Class<?> clazz; try { clazz =
	 * Class.forName(name, true, LogManager.class.getClassLoader()); } catch
	 * (ClassNotFoundException e) { throw new IllegalStateException(
	 * "Failed to load loggerFactory: " + name, e); }
	 * 
	 * Object obj; try { obj = clazz.newInstance(); } catch
	 * (ReflectiveOperationException e) { throw new IllegalStateException(
	 * "Failed to instantiate loggerFactory: " + name, e); }
	 * 
	 * try { factory = (LoggerFactory) obj; } catch (ClassCastException e) {
	 * throw new IllegalStateException("Failed to cast loggerFactory: " + name,
	 * e); }
	 * 
	 * Logger log = factory.getLogger(LogManager.class); log.info(
	 * "Using LoggerFactory Implementation: " + factory.getClass().getName()); }
	 */

	private LogManager() {
	}

	public static Logger getLogger(final Class<?> clazz) {
		return factory.getLogger(clazz);
	}

	@SuppressWarnings("unused")
	private static String getNameFromResource(ClassLoader loader, String resourceName) {
		String name = null;
		if ((loader != null) && (resourceName != null)) {
			try (InputStream inStream = loader.getResourceAsStream(resourceName)) {
				if (inStream != null) {
					Properties properties = new Properties();
					properties.load(inStream);
					name = properties.getProperty(FACTORY_KEY);
					if ("".equals(name)) {
						name = null;
					}
				}
			} catch (IOException | IllegalArgumentException ignore) {
			}
		}
		return null;
	}

	public static Logger getLogger(final String name) {
		return factory.getLogger(name);
	}

	@SuppressWarnings("unused")
	private static String getNameFromSystemProperty() {
		String name = null;
		try {
			name = System.getProperty(FACTORY_KEY);
			if ("".equals(name)) {
				name = null;
			}
		} catch (SecurityException e) {
		}

		return name;
	}
}
