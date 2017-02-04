package in.socyal;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/*import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;*/

import org.apache.commons.lang3.StringUtils;

public class AbstractRestClient {/*
	private Client client = ClientBuilder.newClient().register(AddHeadersFilter.INSTANCE);
	protected static String context;
	protected WebTarget target;
	
	public enum AddHeadersFilter implements ClientRequestFilter {
		INSTANCE;

		private AddHeadersFilter() {
		}

		@Override
		public void filter(ClientRequestContext requestContext) throws IOException {
			
			 * String token = username + ":" + password; String base64Token =
			 * Base64.encodeBase64String(token.getBytes(StandardCharsets.UTF_8))
			 * ;
			 
			// requestContext.getHeaders().add("Authorization", "Basic " +
			// base64Token);
			// requestContext.getHeaders().add("X-Requested-With",
			// "XMLHttpRequest");
			requestContext.getHeaders().add("Accept", "application/json");

		}
	}
	
	protected <T> T invokeGet(String url, Map<String, Object> templateParams, Map<String, Object> queryParams, Class<T> targetEntity) {
		return invoke(url, HttpMethod.GET, templateParams, queryParams, null, targetEntity);
	}
	
	protected <T> T invokePost(String url, Map<String, Object> templateParams, Map<String, Object> queryParams, Object data, Class<T> targetEntity) {
		return invoke(url, HttpMethod.POST, templateParams, queryParams, data, targetEntity);
	}
	
	protected <T> T invokePut(String url, Map<String, Object> templateParams, Map<String, Object> queryParams, Class<T> targetEntity) {
		return invoke(url, HttpMethod.PUT, templateParams, queryParams, null, targetEntity);
	}
	
	protected <T> T invoke(String url, String method, Map<String, Object> templateParams, Map<String, Object> queryParams, Object data, Class<T> targetEntity) {
		target = client.target(context + url);
		Response response = null;
		StringBuilder path = new StringBuilder();
		if (null != templateParams && !templateParams.isEmpty()) {
			for (Entry<String, Object> entry : templateParams.entrySet()) {
				if (entry.getKey().contains("{")) {
					path.append("/" + entry.getKey());	
				} else {
					path.append("/{" + entry.getKey() + "}");
				}
			}
		}
		if (StringUtils.isNotBlank(path)) {
			target = target.path(path.toString()).resolveTemplates(templateParams);
		}
		
		if (queryParams != null && !queryParams.isEmpty()) {
			for (Entry<String, Object> entry : queryParams.entrySet()) {
				target.queryParam(entry.getKey(), entry.getValue());
			}
		}
		
		if (HttpMethod.GET.equals(method)) {
			response = target.request().get();
		} else if (HttpMethod.POST.equals(method)) {
			response = target.request().post(Entity.entity(data, MediaType.APPLICATION_JSON));
		} else if (HttpMethod.PUT.equals(method)) {
			response = target.request().put(Entity.entity(data, MediaType.APPLICATION_JSON));
		}
		
		T entity = response.readEntity(targetEntity);
		return entity;
	}
*/}
