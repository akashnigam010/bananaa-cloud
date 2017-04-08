package in.socyal.sc.api.recommendation.dto;

import java.io.Serializable;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.user.dto.UserDto;

public class RecommendationDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private DishDto dish;
	private UserDto user;
	private Boolean isActive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DishDto getDish() {
		return dish;
	}

	public void setDish(DishDto dish) {
		this.dish = dish;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
