package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOUNT_GIVEN", schema = "Socyal")
public class DiscountGivenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CHECKIN_ID")
	private Integer checkinId;

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "DISCOUNT_DATETIME")
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
