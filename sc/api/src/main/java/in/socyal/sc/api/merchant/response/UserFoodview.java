package in.socyal.sc.api.merchant.response;

import in.socyal.sc.api.type.RatingColorType;

public class UserFoodview {
	private Integer id;
	private Integer userId;
	private String userName;
	private String userImageUrl;
	private Integer userRatingCount;
	private Integer userFoodviewCount;
	private String rating;
	private String ratingClass;
	private String desc;
	private String timeDiff;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public Integer getUserRatingCount() {
		return userRatingCount;
	}

	public void setUserRatingCount(Integer userRatingCount) {
		this.userRatingCount = userRatingCount;
	}

	public Integer getUserFoodviewCount() {
		return userFoodviewCount;
	}

	public void setUserFoodviewCount(Integer userFoodviewCount) {
		this.userFoodviewCount = userFoodviewCount;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRatingClass() {
		if (rating == "") {
			ratingClass = RatingColorType.R25.getCssClass();
		} else { 
			ratingClass = RatingColorType.getCodeByRating(Float.parseFloat(rating)).getCssClass();
		}
		return ratingClass;
	}

	public void setRatingClass(String ratingClass) {
		this.ratingClass = ratingClass;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}
}
