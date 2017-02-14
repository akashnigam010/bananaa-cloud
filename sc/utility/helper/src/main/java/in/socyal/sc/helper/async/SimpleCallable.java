package in.socyal.sc.helper.async;

import java.util.concurrent.Callable;

public interface SimpleCallable<V> extends Callable<V> {
	@Override
	V call() throws Exception;
}
