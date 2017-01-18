package in.socyal.sc.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import in.socyal.sc.api.login.dto.FacebookUser;
import in.socyal.sc.helper.exception.BusinessException;

@Service
public class OAuth2FbHelper {
	public static final String CLIENT_ID = "232637253824009";
	public static final String CLIENT_SECRET = "83b2ab6e7e8b23b6b4a1820c10bab80b";
	public static final String REDIRECT_URI = "http://localhost:8087/socyal/login/fbLoginWithCode";

	public FacebookUser getUserGraphDataWithCode(String code) throws BusinessException, IOException {
		String accessToken;
		accessToken = getFbAccessToken(code);
		if (accessToken == null) {
			throw new BusinessException();
		}

		return getUserGraphDataWithAccessToken(accessToken);
	}

	public FacebookUser getUserGraphDataWithAccessToken(String accessToken) throws IOException {
		String outputString = "";
		String line = null;
		URL graphApiUrl = getGraphApiUrl(accessToken);
		URLConnection conn = graphApiUrl.openConnection();
		conn.setConnectTimeout(7000);
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = br.readLine()) != null) {
			outputString = outputString + line;
		}
		br.close();
		FacebookUser fbUser = (FacebookUser) new Gson().fromJson(outputString, FacebookUser.class);
		return fbUser;
	}

	private String getFbAccessToken(String code) throws IOException {
		String outputString = "";
		String line = null;
		String accessToken = null;
		URL url = getAccessTokenUrl(code);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(20000);
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = br.readLine()) != null) {
			outputString = outputString + line;
		}
		if (outputString.indexOf("access_token") != -1) {
			accessToken = outputString.substring(13, outputString.indexOf("&"));
		}
		br.close();
		return accessToken;
	}

	private URL getAccessTokenUrl(String code) throws MalformedURLException {
		return new URL("https://graph.facebook.com/oauth/access_token?client_id=" + CLIENT_ID + "&redirect_uri="
				+ REDIRECT_URI + "&client_secret=" + CLIENT_SECRET + "&code=" + code);
	}

	private URL getGraphApiUrl(String accessToken) throws MalformedURLException {
		return new URL("https://graph.facebook.com/me?fields=id,first_name,last_name,link,email,"
				+ "gender,picture&access_token=" + accessToken);
	}
}
