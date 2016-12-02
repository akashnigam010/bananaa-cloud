package in.socyal.sc.api.merchant.dto;

import java.io.Serializable;

public class GetMerchantListRequestDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Double latitude;
	private Double longitude;
	private Integer page;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
