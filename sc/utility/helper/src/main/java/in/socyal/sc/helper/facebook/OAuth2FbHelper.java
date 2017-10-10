package in.socyal.sc.helper.facebook;

import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

import in.socyal.sc.api.helper.exception.BusinessException;
import in.socyal.sc.api.type.error.GenericErrorCodeType;
import in.socyal.sc.api.type.error.LoginErrorCodeType;

@Service
public class OAuth2FbHelper {
	private static final Logger LOG = Logger.getLogger(OAuth2FbHelper.class);
	private ResourceBundle resource = ResourceBundle.getBundle("environment");
	private static final String BANANAA_APP_TOKEN = "bna.fb.app.token";

	/**
	 * Gets following user details from FB for the access token<br>
	 * - id<br>
	 * - first name<br>
	 * - last name<br>
	 * - link<br>
	 * - email<br>
	 * - gender<br>
	 * - picture in 160X160<br>
	 * 
	 * @param accessToken
	 * @return
	 * @throws BusinessException
	 * @throws FacebookOAuthException
	 */
	public User getFbUser(String accessToken) throws BusinessException {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
		User me = null;
		try {
			me = fbClient.fetchObject("me", User.class, Parameter.with("fields",
					"id,first_name,last_name,link,email,gender,picture.width(160).height(160)"));
		} catch (FacebookOAuthException e) {
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}

		return me;
	}

	/**
	 * Publishes a POST on Facebook Timeline <br>
	 * <br>
	 * 
	 * <b>IMPORTANT</b> : Abide FB Policies, do not add extra text along with a
	 * POST<br>
	 * 
	 * <code>https://developers.facebook.com/docs/apps/review/prefill</code><br>
	 * 
	 * <tt>Graph API Version - 2.8</tt>
	 * 
	 * @param accessToken
	 * @param linkToPost
	 * @throws FacebookOAuthException
	 * @throws BusinessException 
	 */
	public void publishToTimeline(String accessToken, String linkToPost) throws BusinessException {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
		try {
			fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "Testing FB Post"),
					Parameter.with("link", linkToPost));
		} catch (FacebookOAuthException e) {
			throw new BusinessException(LoginErrorCodeType.INCORRECT_FB_TOKEN);
		}
	}

	/**
	 * Checks for the validity of the passed Access Token<br>
	 * 
	 * The FacebookClient object is build using <b>BANANAA</b> APP token.<br>
	 * 
	 * An APP token is used to inspect User/Page tokens tied with app (whose APP
	 * token is being used to inspect)
	 * 
	 * @param accessToken
	 *            - access token to be inspected for validity
	 * @throws BusinessException
	 *             - If the passed access token does not belong to the app whose
	 *             APP token is being used to inspect
	 */
	public void checkForTokenValidity(String accessToken) throws BusinessException {
		FacebookClient fbClient = new DefaultFacebookClient(resource.getString(BANANAA_APP_TOKEN), Version.VERSION_2_8);
		DebugTokenInfo tokenInfo = fbClient.debugToken(accessToken);
		if (!tokenInfo.isValid()) {
			LOG.error(
					"***** Invalid facebook access token being passed. Security breach detected.*****. Access Token : "
							+ accessToken);
			throw new BusinessException(GenericErrorCodeType.INVALID_TOKEN);
		}
	}
}
