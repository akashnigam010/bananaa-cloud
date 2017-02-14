package in.socyal.sc.helper.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncExecutorImpl implements AsyncExecutor {
	@Autowired AsyncDelegate delegate;
	
	@Override
	public <V> Future<V> submit(Callable<V> task) {
		return delegate.submit(task);
	}
}
