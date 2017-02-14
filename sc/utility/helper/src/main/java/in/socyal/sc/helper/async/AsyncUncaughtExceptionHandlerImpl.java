package in.socyal.sc.helper.async;

import java.lang.reflect.Method;

import org.jboss.logging.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncUncaughtExceptionHandlerImpl implements AsyncUncaughtExceptionHandler {
	private static final Logger LOG = Logger.getLogger(AsyncUncaughtExceptionHandlerImpl.class);
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		LOG.error("Method name:" + method.getName());
		LOG.error("Exception occured:", ex);
	}
}
