package in.socyal.sc.helper.firebase;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.socyal.sc.api.firebase.FirebaseType;
import in.socyal.sc.api.firebase.FirebaseUser;
import in.socyal.sc.api.firebase.Uid;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.error.GenericErrorCodeType;

@Service
public class FirebaseAuthHelper {
	private static final String GET_LOGIN_URL = "http://localhost:8443/getUserDetails";
	private static final String GET_UID_URL = "http://localhost:8443/verifyIdToken";

	/**
	 * Returns firebase user. If no user is found or the uid is not valid, a
	 * false status is returned along with null user object
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public FirebaseUser getUserDetails(String uid) throws BusinessException {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject request = new JSONObject();
		request.put(FirebaseType.UID.getValue(), uid);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(request.toJSONString(), headers);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String answer = restTemplate.postForObject(GET_LOGIN_URL, entity, String.class);
			return mapper.readValue(answer, FirebaseUser.class);
		} catch (ResourceAccessException e) {
			System.out.println("************** NODE SERVER DOWN **************");
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		} catch (IOException e) {
			System.out.println("Error occurred while parsing response from Firebase");
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}

	}

	/**
	 * Returns uid from the idToken. If the idToken is expired or is not valid,
	 * false status is returned along with null uid
	 * 
	 * @param idToken
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public Uid getUid(String idToken) throws BusinessException {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject request = new JSONObject();
		request.put(FirebaseType.ID_TOKEN.getValue(), idToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(request.toJSONString(), headers);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String answer = restTemplate.postForObject(GET_UID_URL, entity, String.class);
			return mapper.readValue(answer, Uid.class);
		} catch (ResourceAccessException e) {
			System.out.println("************** NODE SERVER DOWN **************");
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		} catch (IOException e) {
			System.out.println("Error occurred while parsing response from Firebase");
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}

	}
}
