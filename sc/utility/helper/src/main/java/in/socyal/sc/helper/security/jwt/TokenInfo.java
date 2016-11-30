package in.socyal.sc.helper.security.jwt;

import org.joda.time.DateTime;

public class TokenInfo {
	private String userId;
	private DateTime issueDate;
	private DateTime expires;
	private String features;
	private String role;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public DateTime getIssued() {
		return issueDate;
	}

	public void setIssued(DateTime issued) {
		this.issueDate = issued;
	}

	public DateTime getExpires() {
		return expires;
	}

	public void setExpires(DateTime expires) {
		this.expires = expires;
	}

	public DateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(DateTime issueDate) {
		this.issueDate = issueDate;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "TokenInfo [userId=" + userId + ", features=" + features + ", issued=" + issueDate + ", expires="
				+ expires + "]";
	}
}