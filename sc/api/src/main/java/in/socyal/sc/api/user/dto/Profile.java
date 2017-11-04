package in.socyal.sc.api.user.dto;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String imageUrl;
	private String firstName;
	private String lastName;
	private Float level;
	private String status;
	private Integer vegnonvegId;
	private Integer ratingCount;
	private Integer foodviewCount;
	private List<Tag> cuisines;
	private List<Tag> dishes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Float getLevel() {
		return level;
	}

	public void setLevel(Float level) {
		this.level = level;
	}

	public Integer getVegnonvegId() {
		return vegnonvegId;
	}

	public void setVegnonvegId(Integer vegnonvegId) {
		this.vegnonvegId = vegnonvegId;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Integer getFoodviewCount() {
		return foodviewCount;
	}

	public void setFoodviewCount(Integer foodviewCount) {
		this.foodviewCount = foodviewCount;
	}

	public List<Tag> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<Tag> cuisines) {
		this.cuisines = cuisines;
	}

	public List<Tag> getDishes() {
		return dishes;
	}

	public void setDishes(List<Tag> dishes) {
		this.dishes = dishes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
