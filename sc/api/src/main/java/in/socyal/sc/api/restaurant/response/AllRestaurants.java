package in.socyal.sc.api.restaurant.response;

import java.util.ArrayList;
import java.util.List;

public class AllRestaurants {
	private Integer restaurantId;
	private String restaurantName;
	private String address;
	private String checkinCount;
	private String restaurantImageUrl;
	private String rating;
	private List<String> primaryRewards;

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(String checkinCount) {
		this.checkinCount = checkinCount;
	}

	public String getRestaurantImageUrl() {
		return restaurantImageUrl;
	}

	public void setRestaurantImageUrl(String restaurantImageUrl) {
		this.restaurantImageUrl = restaurantImageUrl;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public List<String> getPrimaryRewards() {
		if (primaryRewards == null) {
			return new ArrayList<>();
		}
		return primaryRewards;
	}

	public void setPrimaryRewards(List<String> primaryRewards) {
		this.primaryRewards = primaryRewards;
	}
}
