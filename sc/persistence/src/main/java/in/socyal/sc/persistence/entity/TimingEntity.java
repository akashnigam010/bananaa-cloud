package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import in.socyal.sc.api.type.DayType;

@Entity
@Table(name = "TIMING", schema = "bna")
public class TimingEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "MERCHANT_ID")
	private Integer merchantId;

	@Column(name = "DAY")
	@Enumerated(EnumType.STRING)
	private DayType day;

	@Column(name = "OPEN")
	private String open;

	@Column(name = "CLOSE")
	private String close;

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

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}
}
