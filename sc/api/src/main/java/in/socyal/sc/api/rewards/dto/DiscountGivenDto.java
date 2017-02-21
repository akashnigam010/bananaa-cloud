package in.socyal.sc.api.rewards.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class DiscountGivenDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer checkinId;
	private BigDecimal amount;
	private Calendar discountDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Integer checkinId) {
		this.checkinId = checkinId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Calendar getDiscountDateTime() {
		return discountDateTime;
	}

	public void setDiscountDateTime(Calendar discountDateTime) {
		this.discountDateTime = discountDateTime;
	}
}
