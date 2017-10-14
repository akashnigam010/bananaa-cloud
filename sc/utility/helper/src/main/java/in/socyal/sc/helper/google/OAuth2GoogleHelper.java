package in.socyal.sc.helper.google;

import java.io.IOException;
import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import in.socyal.sc.api.google.GoogleUser;
import in.socyal.sc.api.google.TokenInfo;
import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.LoginErrorCodeType;

@Service
public class OAuth2GoogleHelper {
	private static final Logger LOG = Logger.getLogger(OAuth2GoogleHelper.class);
	private ResourceBundle resource = ResourceBundle.getBundle("environment");
	private static final String GET_USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=";
	private static final String VERIFY_ACCESS_TOKEN_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=";
	private static final String REVOKE_ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/revoke?token=";
	private static final String GOOGLE_CLIENT_ID = "bna.google.client.id";
	private static final String GOOGLE_CLIENT_SECRET = "bna.google.client.secret";

	/**
	 * First checks for the validity/authenticity of the passed access token.
	 * Then hits google api to get token information
	 * 
	 * @param accessToken
	 * @return
	 * @throws BusinessException
	 */
	private TokenInfo getTokenInfo(String accessToken) throws BusinessException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String answer = restTemplate.getForObject(VERIFY_ACCESS_TOKEN_URL + accessToken, String.class);
			TokenInfo token = mapper.readValue(answer, TokenInfo.class);
			if (token.getIssued_to().equals(resource.getString(GOOGLE_CLIENT_ID))
					&& Integer.parseInt(token.getExpires_in()) > 0) {
				return token;
			}
			LOG.error("***** Invalid google access token being passed. Security breach detected.*****. Access Token : "
					+ accessToken);
			throw new BusinessException(GenericErrorCodeType.INVALID_TOKEN);
		} catch (Exception e) {
			LOG.error("***** Invalid google access token being passed. Security breach detected.*****. Access Token : "
					+ accessToken);
			throw new BusinessException(GenericErrorCodeType.INVALID_TOKEN);
		}
	}

	/**
	 * Gets user info using the passed access token
	 * 
	 * @param accessToken
	 * @return
	 * @throws BusinessException
	 */
	public GoogleUser getGoogleUser(String accessToken) throws BusinessException {
		getTokenInfo(accessToken);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String answer = restTemplate.getForObject(GET_USER_INFO_URL + accessToken, String.class);
			return mapper.readValue(answer, GoogleUser.class);
		} catch (Exception e) {
			LOG.error("Error occurred while getting google user details. Access Token : " + accessToken, e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	public void revokeToken(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.getForObject(REVOKE_ACCESS_TOKEN_URL + accessToken, String.class);
		} catch (Exception e) {
			LOG.error(
					"***** Exception occurred while revoking google access token.*****. Access Token : " + accessToken);
		}
	}

	public GoogleUser getGoogleUserNew(String serverAuthToken) throws BusinessException {
		GoogleIdToken idToken;
		try {
			GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
					JacksonFactory.getDefaultInstance(), "https://www.googleapis.com/oauth2/v4/token",
					resource.getString(GOOGLE_CLIENT_ID), resource.getString(GOOGLE_CLIENT_SECRET), serverAuthToken, "")
							.execute();
			idToken = tokenResponse.parseIdToken();
		} catch (IOException e) {
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}
		GoogleIdToken.Payload payload = idToken.getPayload();
		GoogleUser googleUser = new GoogleUser();
		googleUser.setId(payload.getSubject());
		googleUser.setName((String) payload.get("name"));
		googleUser.setPicture((String) payload.get("picture"));
		googleUser.setEmail(payload.getEmail());
		return googleUser;
	}
}
