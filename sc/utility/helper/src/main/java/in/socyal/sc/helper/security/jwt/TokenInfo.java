package in.socyal.sc.helper.security.jwt;

import org.joda.time.DateTime;

public class TokenInfo {
	private String userId;
	private String firstName;
	private String nameId;
	private DateTime issueDate;
	private DateTime expires;
	private String features;
	private String role;
	private String deviceId;
	private String merchantId;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
}