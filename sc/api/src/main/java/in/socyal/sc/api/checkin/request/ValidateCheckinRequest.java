package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

import in.socyal.sc.api.merchant.request.LocationRequest;

public class ValidateCheckinRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private LocationRequest location;
	private String qrCode;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public LocationRequest getLocation() {
		return location;
	}

	public void setLocation(LocationRequest location) {
		this.location = location;
	}
}
