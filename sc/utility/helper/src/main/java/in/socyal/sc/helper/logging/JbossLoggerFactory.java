package in.socyal.sc.helper.logging;

public class JbossLoggerFactory implements LoggerFactory {
	public Logger getLogger(String name) {
		return new JbossLogger(getJbossLogger(name));
	}

	public Logger getLogger(Class<?> clazz) {
		return new JbossLogger(getJbossLogger(clazz));
	}

	org.jboss.logging.Logger getJbossLogger(final String name) {
		if (name == null) {
			throw new UnsupportedOperationException("No name provided.");
		}

		return org.jboss.logging.Logger.getLogger(name);
	}

	org.jboss.logging.Logger getJbossLogger(final Class<?> clazz) {
		if (clazz == null) {
			throw new UnsupportedOperationException("No class provided.");
		}

		return org.jboss.logging.Logger.getLogger(clazz);
	}
}
