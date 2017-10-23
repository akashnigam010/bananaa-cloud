package in.socyal.sc.api.merchant.request;

import java.io.Serializable;

import in.socyal.sc.api.location.dto.Location;

public class GetMerchantListRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Location location;
	private Integer page;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
