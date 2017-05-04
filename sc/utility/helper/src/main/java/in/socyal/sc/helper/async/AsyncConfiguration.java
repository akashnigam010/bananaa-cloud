//package in.socyal.sc.helper.async;
//
//import java.util.concurrent.Executor;
//
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//@Configuration
//@EnableAsync
//public class AsyncConfiguration implements AsyncConfigurer {
//
//	@Override
//	public Executor getAsyncExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(10);
//		executor.setMaxPoolSize(50);
//		executor.setQueueCapacity(150);
//		executor.setThreadNamePrefix("Bananaa-Async-");
//		executor.initialize();
//		return executor;
//	}
//
//	@Override
//	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//		return new AsyncUncaughtExceptionHandlerImpl();
//	}
//}
