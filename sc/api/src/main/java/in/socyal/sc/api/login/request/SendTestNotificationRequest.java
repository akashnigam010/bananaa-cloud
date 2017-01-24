package in.socyal.sc.api.login.request;

import java.util.List;

public class SendTestNotificationRequest {
	private String deviceToken;
	private List<Data> data;

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
}
