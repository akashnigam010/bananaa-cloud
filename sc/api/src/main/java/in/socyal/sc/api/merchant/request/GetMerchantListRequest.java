package in.socyal.sc.api.merchant.request;

import java.io.Serializable;

public class GetMerchantListRequest implements Serializable {
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
