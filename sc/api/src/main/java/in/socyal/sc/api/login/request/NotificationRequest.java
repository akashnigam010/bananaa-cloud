package in.socyal.sc.api.login.request;

import java.util.ArrayList;
import java.util.List;

public class NotificationRequest {
	private String title;
	private String body;
	private List<Data> data;
	private List<String> deviceTokens;

	public List<String> getDeviceTokens() {
		if (deviceTokens == null) {
			deviceTokens = new ArrayList<>();
		}
		return deviceTokens;
	}

	public void setDeviceTokens(List<String> deviceTokens) {
		this.deviceTokens = deviceTokens;
	}

	public List<Data> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
