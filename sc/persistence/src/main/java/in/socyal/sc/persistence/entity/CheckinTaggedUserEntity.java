package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHECKIN_TAGGED_USER_MAPPING", schema = "Socyal")
public class CheckinTaggedUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CHECKIN_ID")
	private Integer checkinId;
	
	@Column(name = "USER_ID")
	private Integer userId;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
