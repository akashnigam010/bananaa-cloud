package in.socyal.sc.helper.facebook;

import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

@Service
public class OAuth2FbHelper {
	private ResourceBundle resource = ResourceBundle.getBundle("bananaa-application");
	private static final String BANANAA_APP_TOKEN = "bananaa.fb.app.token";

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
	 * @throws FacebookOAuthException
	 */
	public User getFbUser(String accessToken) throws FacebookOAuthException {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
		User me = fbClient.fetchObject("me", User.class,
				Parameter.with("fields", "id,first_name,last_name,link,email,gender,picture.width(160).height(160)"));
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
	 */
	public void publishToTimeline(String accessToken, String linkToPost) throws FacebookOAuthException {
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
		fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "Testing FB Post"),
				Parameter.with("link", linkToPost));
	}

	/**
	 * Checks for the validity of the passed Access Token<br>
	 * 
	 * The FacebookClient object is build using <b>BANANAA</b> APP token.<br>
	 * 
	 * An APP token is a special token which is used to inspect User/Page tokens
	 * tied with app (whose APP token is being used to inspect)
	 * 
	 * @param accessToken
	 *            - access token to be inspected for validity
	 * @throws FacebookOAuthException
	 *             - If the passed access token does not belong to the app whose
	 *             APP token is being used to inspect
	 * @return Is token valid
	 */
	public boolean checkForTokenValidity(String accessToken) throws FacebookOAuthException {
		FacebookClient fbClient = new DefaultFacebookClient(resource.getString(BANANAA_APP_TOKEN), Version.VERSION_2_8);
		DebugTokenInfo tokenInfo = fbClient.debugToken(accessToken);
		return tokenInfo.isValid();
	}
}
