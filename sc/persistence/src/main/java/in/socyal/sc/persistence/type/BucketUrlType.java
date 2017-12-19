package in.socyal.sc.persistence.type;

public enum BucketUrlType {
	URL_PREFIX("https://bna-ohio.s3.amazonaws.com/"),
	USER_PREFIX("https://bna-usr.s3.amazonaws.com/");
	
	private String urlPrefix;

	private BucketUrlType(String urlPrefix) {
		this.setUrlPrefix(urlPrefix);
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

}
