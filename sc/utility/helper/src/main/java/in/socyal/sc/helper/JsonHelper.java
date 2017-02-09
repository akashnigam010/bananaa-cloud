package in.socyal.sc.helper;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonHelper {
	private static Logger LOG;
	/**
	 * This method is used to print an object in JSON format
	 * @param object
	 * @param clazz
	 * @param requestType
	 */
	@SuppressWarnings("rawtypes")
	public static void logRequest(Object object, Class clazz, String serviceName) {
		LOG = Logger.getLogger(clazz);
		StringBuilder request = new StringBuilder();
		request.append("Service : ");
		request.append(serviceName);
		request.append("\n");
		try {
	        if (object != null) {
		        ObjectMapper mapper = new ObjectMapper();
				request.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
	        }
		} catch (JsonProcessingException e) {
			LOG.error("Exception occured while parsing %s to json", object.getClass(), e);
		}
		LOG.info(request);
    }
}
