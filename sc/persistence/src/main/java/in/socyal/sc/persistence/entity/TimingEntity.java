package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIMING", schema = "SOCYAL")
public class TimingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "OPEN_TIME")
	private Double openTime;

	@Column(name = "CLOSE_TIME")
	private Double closeTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Double openTime) {
		this.openTime = openTime;
	}

	public Double getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Double closeTime) {
		this.closeTime = closeTime;
	}
}
