package in.socyal.sc.api.login.request;

public class LoginRequest {
	private ClientType client;
	private String accessToken;

	public ClientType getClient() {
		return client;
	}

	public void setClient(ClientType client) {
		this.client = client;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
