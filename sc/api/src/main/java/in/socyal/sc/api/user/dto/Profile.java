package in.socyal.sc.api.user.dto;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String imageUrl;
	private String name;
	private Float level;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
