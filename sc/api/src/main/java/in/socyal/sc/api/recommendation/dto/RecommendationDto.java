package in.socyal.sc.api.recommendation.dto;

import java.io.Serializable;
import java.util.Calendar;

import in.socyal.sc.api.dish.dto.DishDto;
import in.socyal.sc.api.type.RatingColorType;
import in.socyal.sc.api.user.dto.UserDto;

public class RecommendationDto implements Serializable, Comparable<RecommendationDto> {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private DishDto dish;
	private UserDto user;
	private Float rating;
	private String ratingClass;
	private String description;
	private Calendar createdDateTime;
	private Calendar updatedDateTime;
	private String timeDiff;

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

	public Calendar getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Calendar getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Calendar updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(RecommendationDto o) {
		return o.updatedDateTime.compareTo(this.updatedDateTime);
	}

	public String getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getRatingClass() {
		if (rating == 0f) {
			ratingClass = RatingColorType.R25.getCssClass();
		} else {
			ratingClass = RatingColorType.getCodeByRating(rating).getCssClass();
		}
		return ratingClass;
	}

	public void setRatingClass(String ratingClass) {
		this.ratingClass = ratingClass;
	}
}
