package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

import in.socyal.sc.api.merchant.dto.Location;

public class ValidateCheckinRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Location location;
	private String qrCode;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
