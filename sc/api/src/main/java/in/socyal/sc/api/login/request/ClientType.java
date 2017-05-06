package in.socyal.sc.api.login.request;

public enum ClientType {
	GOOGLE("google"), 
	FACEBOOK("facebook");
	private String client;

	private ClientType(String client) {
		this.client = client;
	}

	public String getClient() {
		return client;
	}
}
