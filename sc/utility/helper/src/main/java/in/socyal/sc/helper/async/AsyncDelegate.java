package in.socyal.sc.helper.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class AsyncDelegate {
	private static final Logger LOG = Logger.getLogger(AsyncDelegate.class);
	
	@Async
	public <V> Future<V> submit(Callable<V> task) {
		try {
			return new AsyncResult<>(task.call());
		} catch (Exception e) {
			LOG.error("Exception occured during async call:", e);
			return new ExceptionFuture<>(e);
		}
	}
}
