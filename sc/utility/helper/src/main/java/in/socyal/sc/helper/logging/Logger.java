package in.socyal.sc.helper.logging;

public interface Logger {
	String getName();

	boolean isDebugEnabled();

	boolean isErrorEnabled();

	boolean isFatalEnabled();

	boolean isInfoEnabled();

	boolean isTraceEnabled();

	boolean isWarnEnabled();

	void debug(Object message, Throwable t);

	void debug(String message);

	void debug(String message, Object... params);

	void debug(String message, Throwable t);

	void error(Object message, Throwable t);

	void error(String message);

	void error(String message, Object... params);

	void error(String message, Throwable t);

	void fatal(Object message, Throwable t);

	void fatal(String message);

	void fatal(String message, Object... params);

	void fatal(String message, Throwable t);

	void info(Object message, Throwable t);

	void info(String message);

	void info(String message, Object... params);

	void info(String message, Throwable t);

	void trace(Object message, Throwable t);

	void trace(String message);

	void trace(String message, Object... params);

	void trace(String message, Throwable t);

	void warn(Object message, Throwable t);

	void warn(String message);

	void warn(String message, Object... params);

	void warn(String message, Throwable t);
}
