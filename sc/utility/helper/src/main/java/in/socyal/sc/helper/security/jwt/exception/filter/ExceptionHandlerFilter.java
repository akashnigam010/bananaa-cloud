package in.socyal.sc.helper.security.jwt.exception.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.socyal.sc.api.ArrayOfStatusCode;
import in.socyal.sc.api.StatusCode;
import in.socyal.sc.api.response.GenericResponse;
import in.socyal.sc.helper.exception.BusinessException;
import in.socyal.sc.helper.type.GenericErrorCodeType;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
	private static final Logger LOG = Logger.getLogger(ExceptionHandlerFilter.class);
	private ResourceBundle resource = ResourceBundle.getBundle("socyal-error-code");

    @Override
    public void doFilterInternal(HttpServletRequest request, 
    							 HttpServletResponse response, 
    							 FilterChain filterChain) 
    							 throws ServletException, IOException {
    	//This will be the default error code and message, if resource bundle doesn't have error message
    	Integer errorCode = GenericErrorCodeType.GENERIC_ERROR.getBusinessErrorCode();
    	String exceptionMessage = "Something went wrong! Please try again!";
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            //Check whether RuntimeException is BusinessException or not
            if (e instanceof BusinessException) {
            	BusinessException exception = (BusinessException) e;
            	if (exception.getErrorCodes() != null && !exception.getErrorCodes().isEmpty()) {
            		errorCode = ((BusinessException) e).getErrorCodes().get(0);
            		try {
            			exceptionMessage = resource.getString(errorCode.toString());
            		} catch (MissingResourceException mre) {
            			LOG.error("Resource missing for key:" + errorCode + " in 'socyal-error-code' resource bundle");
	            		}
            	}
            }
            GenericResponse errorResponse = new GenericResponse();
            errorResponse.setResult(Boolean.FALSE);
            ArrayOfStatusCode statusCodes = new ArrayOfStatusCode();
            StatusCode statusCode = new StatusCode();
            statusCode.setCode(errorCode);
            statusCode.setDescription(exceptionMessage);
            statusCodes.setStatusCode(Collections.singletonList(statusCode));
            errorResponse.setStatusCodes(statusCodes);
            response.getWriter().write(convertObjectToJson(errorResponse));
    }
}

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
