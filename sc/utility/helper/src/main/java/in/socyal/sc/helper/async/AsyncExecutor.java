package in.socyal.sc.helper.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface AsyncExecutor {
	<V> Future<V> submit(Callable<V> task);
}
