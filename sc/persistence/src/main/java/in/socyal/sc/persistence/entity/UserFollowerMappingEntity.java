package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_FOLLOWER_MAPPING", schema = "Socyal")
public class UserFollowerMappingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "FOLLOWER_USER_ID")
	private UserEntity followerUser;

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

	public UserEntity getFollowerUser() {
		return followerUser;
	}

	public void setFollowerUser(UserEntity followerUser) {
		this.followerUser = followerUser;
	}
}
