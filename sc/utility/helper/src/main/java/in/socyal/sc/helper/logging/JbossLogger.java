package in.socyal.sc.helper.logging;

import java.text.MessageFormat;
import java.util.Objects;

public class JbossLogger implements Logger {
	protected org.jboss.logging.Logger log;

	public JbossLogger(org.jboss.logging.Logger log) {
		this.log = log;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(log);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != getClass())) {
			return false;
		}

		JbossLogger other = (JbossLogger) obj;
		return Objects.equals(log, other.log);
	}

	@Override
	public String toString() {
		return log.toString();
	}

	@Override
	public String getName() {
		return log.getName();
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isEnabled(org.jboss.logging.Logger.Level.DEBUG);
	}

	@Override
	public boolean isErrorEnabled() {
		return log.isEnabled(org.jboss.logging.Logger.Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return log.isEnabled(org.jboss.logging.Logger.Level.FATAL);
	}

	@Override
	public boolean isTraceEnabled() {
		return log.isEnabled(org.jboss.logging.Logger.Level.TRACE);
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isEnabled(org.jboss.logging.Logger.Level.WARN);
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isEnabled(org.jboss.logging.Logger.Level.INFO);
	}

	@Override
	public void debug(Object message, Throwable t) {
		log.debug(message, t);
	}

	@Override
	public void debug(String message) {
		log.debug(message);
	}

	@Override
	public void debug(String message, Object... params) {
		log.debug(formatMessage(message, params));
	}

	@Override
	public void debug(String message, Throwable t) {
		log.debug(message, t);
	}

	@Override
	public void error(Object message, Throwable t) {
		log.error(message, t);
	}

	@Override
	public void error(String message) {
		log.error(message);
	}

	@Override
	public void error(String message, Object... params) {
		log.error(formatMessage(message, params));
	}

	@Override
	public void error(String message, Throwable t) {
		log.error(message, t);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		log.fatal(message, t);
	}

	@Override
	public void fatal(String message) {
		log.fatal(message);
	}

	@Override
	public void fatal(String message, Object... params) {
		log.fatal(formatMessage(message, params));
	}

	@Override
	public void fatal(String message, Throwable t) {
		log.fatal(message, t);
	}

	@Override
	public void info(Object message, Throwable t) {
		log.info(message, t);
	}

	@Override
	public void info(String message) {
		log.info(message);
	}

	@Override
	public void info(String message, Object... params) {
		log.info(formatMessage(message, params));
	}

	@Override
	public void info(String message, Throwable t) {
		log.info(message, t);
	}

	@Override
	public void trace(Object message, Throwable t) {
		log.trace(message, t);
	}

	@Override
	public void trace(String message) {
		log.trace(message);
	}

	@Override
	public void trace(String message, Object... params) {
		log.trace(formatMessage(message, params));
	}

	@Override
	public void trace(String message, Throwable t) {
		log.trace(message, t);
	}

	@Override
	public void warn(Object message, Throwable t) {
		log.warn(message, t);
	}

	@Override
	public void warn(String message) {
		log.warn(message);
	}

	@Override
	public void warn(String message, Object... params) {
		log.warn(formatMessage(message, params));
	}

	@Override
	public void warn(String message, Throwable t) {
		log.warn(message, t);
	}

	protected String formatMessage(String format, Object... args) {
		try {
			return MessageFormat.format(format, args);
		} catch (IllegalArgumentException e) {
			log.error("Unable to format message : " + format, e);
			return format;
		}
	}
}
