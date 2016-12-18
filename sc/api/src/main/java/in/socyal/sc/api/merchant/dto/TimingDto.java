package in.socyal.sc.api.merchant.dto;

import java.io.Serializable;

import in.socyal.sc.api.type.DayType;

public class TimingDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer merchantId;
	private DayType day;
	private Integer open;
	private Integer close;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public DayType getDay() {
		return day;
	}

	public void setDay(DayType day) {
		this.day = day;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public Integer getClose() {
		return close;
	}

	public void setClose(Integer close) {
		this.close = close;
	}
}
