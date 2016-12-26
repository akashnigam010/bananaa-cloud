package in.socyal.sc.api.checkin.request;

import java.io.Serializable;

import in.socyal.sc.api.merchant.request.LocationRequest;

public class AroundMeCheckinsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private LocationRequest location;
	private Integer page;

	public LocationRequest getLocation() {
		return location;
	}

	public void setLocation(LocationRequest location) {
		this.location = location;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
