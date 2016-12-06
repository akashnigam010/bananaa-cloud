package in.socyal.sc.persistence.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.web.servlet.handler. HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	static Logger LOG = Logger.getLogger(LoggerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
        final StringBuilder logMessage = new StringBuilder("REST Request - ").append("[HTTP METHOD:")
                .append(request.getMethod())
                .append("] \n [REQUEST PARAMETERS:").append(getBodyResquest(request))
                .append("] \n [REMOTE ADDRESS:")
                .append(request.getRemoteAddr()).append("]");
		LOG.info(logMessage);
		return true;
	}
	
	public static String getBodyResquest(HttpServletRequest request) throws IOException {
	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
	
	/*@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
        final StringBuilder logMessage = new StringBuilder("REST Response - ")
                .append("] \n [RESPONSE PARAMETERS:").append(getBody(response))
                .append("]");
		LOG.info(logMessage);
	}*/
	
	/*@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("After rendering the view");
		super.afterCompletion(request, response, handler, ex);
	}*/
}
