package in.socyal.sc.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
public class LogAspect {
	private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

	@Autowired ObjectMapper objectMapper;
	
	@Around("execution(public * in.socyal.sc.controller.*.*(..))")
	public Object logAfter(ProceedingJoinPoint joinPoint) {
		long started = System.currentTimeMillis();
		Object object = null;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("Before: "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
			for(Object arg : joinPoint.getArgs()) {
				sb.append("("+objectMapper.writeValueAsString(arg)+", ");
			}
			if(sb.indexOf(", ") > -1) {
				sb.delete(sb.length()-2, sb.length()-1);
				sb.append(")");
			}
			LOG.trace("arguements: "+sb);
			object = joinPoint.proceed();
			long duration = System.currentTimeMillis() - started;
			LOG.trace("Completed in ["+duration+" ms] "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()
				+" [Returned: "+objectMapper.writeValueAsString(object)+"]");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		 return object;
	}
}
