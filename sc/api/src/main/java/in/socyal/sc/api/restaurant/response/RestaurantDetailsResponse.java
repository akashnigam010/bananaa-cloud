package in.socyal.sc.api.restaurant.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.socyal.sc.api.response.GenericResponse;

public class RestaurantDetailsResponse extends GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer restaurantId;
	private String restaurantName;
	private String rating;
	private String address;
	private String checkinCount;
	private List<String> rewards;
	private Integer userPreviousCheckinCount;
	private List<String> recentCheckins; // (paginate - chunk of 10)
	private String restaurantImageUrl;

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

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
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

	public List<String> getRewards() {
		if (rewards == null) {
				return new ArrayList<>();
			}
		return rewards;
	}

	public void setRewards(List<String> rewards) {
		this.rewards = rewards;
	}

	public Integer getUserPreviousCheckinCount() {
		return userPreviousCheckinCount;
	}

	public void setUserPreviousCheckinCount(Integer userPreviousCheckinCount) {
		this.userPreviousCheckinCount = userPreviousCheckinCount;
	}

	public List<String> getRecentCheckins() {
		if (recentCheckins == null) {
			return new ArrayList<>();
		}
		return recentCheckins;
	}

	public void setRecentCheckins(List<String> recentCheckins) {
		this.recentCheckins = recentCheckins;
	}

	public String getRestaurantImageUrl() {
		return restaurantImageUrl;
	}

	public void setRestaurantImageUrl(String restaurantImageUrl) {
		this.restaurantImageUrl = restaurantImageUrl;
	}
}
