package in.socyal.sc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RECOMMENDATION", schema = "bna")
public class RecommendationEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "DISH_ID")
	private DishEntity dish;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;

	@Column(name = "DESCRIPTION")
	private String description;

	public DishEntity getDish() {
		return dish;
	}

	public void setDish(DishEntity dish) {
		this.dish = dish;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
