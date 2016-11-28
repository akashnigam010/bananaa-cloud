package in.socyal.sc.api.restaurant.request;

import java.io.Serializable;

public class AllRestaurantsRequest implements Serializable {
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
