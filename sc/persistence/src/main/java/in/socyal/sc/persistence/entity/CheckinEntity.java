package in.socyal.sc.persistence.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import in.socyal.sc.api.type.CheckinStatusType;

@Entity
@Table(name = "CHECKIN", schema = "Socyal")
public class CheckinEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private UserEntity user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID")
	private MerchantEntity merchant;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private CheckinStatusType status;
	
	@Column(name = "QR_CODE")
	private String qrCode;
	
	@Column(name = "REWARD_MESSAGE")
	private String rewardMessage;
	
	@Column(name = "CHECKIN_DATETIME")
	private Calendar checkinDateTime;
	
	@Column(name = "APPROVED_DATETIME")
	private Calendar approvedDateTime;
	
	@Column(name = "UPDATED_DATETIME")
	private Calendar updatedDateTime;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKIN_ID", referencedColumnName = "ID")
	private Set<CheckinTaggedUserEntity> taggedUsers;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKIN_ID", referencedColumnName = "ID")
	private Set<CheckinUserLikeEntity> likes;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	public CheckinStatusType getStatus() {
		return status;
	}

	public void setStatus(CheckinStatusType status) {
		this.status = status;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}

	public void setRewardMessage(String rewardMessage) {
		this.rewardMessage = rewardMessage;
	}

	public Calendar getCheckinDateTime() {
		return checkinDateTime;
	}

	public void setCheckinDateTime(Calendar checkinDateTime) {
		this.checkinDateTime = checkinDateTime;
	}

	public Calendar getApprovedDateTime() {
		return approvedDateTime;
	}

	public void setApprovedDateTime(Calendar approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

	public Calendar getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Calendar updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public Set<CheckinTaggedUserEntity> getTaggedUsers() {
		return taggedUsers;
	}

	public void setTaggedUsers(Set<CheckinTaggedUserEntity> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public Set<CheckinUserLikeEntity> getLikes() {
		return likes;
	}

	public void setLikes(Set<CheckinUserLikeEntity> likes) {
		this.likes = likes;
	}
}
