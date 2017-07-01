package in.socyal.sc.api.item.response;

import in.socyal.sc.api.cuisine.dto.CuisineDto;
import in.socyal.sc.api.suggestion.dto.SuggestionDto;
import in.socyal.sc.api.type.RatingColorType;
import in.socyal.sc.api.type.TagType;

public class Tag implements Comparable<Tag> {
	private Integer id;
	private String nameId;
	private String name;
	private String thumbnail;
	private Integer dishCount;
	private String itemUrl;
	private String ratingClass;
	private String rating;
	private String imageUrl;

	public String getRatingClass() {
		if (rating == "") {
			ratingClass = RatingColorType.R25.getCssClass();
		} else { 
			ratingClass = RatingColorType.getCodeByRating(Float.parseFloat(rating)).getCssClass();
		}
		return ratingClass;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String imageUrl) {
		this.thumbnail = imageUrl;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public void setRatingClass(String ratingClass) {
		this.ratingClass = ratingClass;
	}

	public Integer getDishCount() {
		return dishCount;
	}

	public void setDishCount(Integer dishCount) {
		this.dishCount = dishCount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int compareTo(Tag o) {
		return o.rating.compareTo(this.rating);
	}
	
	public TagType getTagType() {
		if (this instanceof CuisineDto) {
			return TagType.CUISINE;
		} else if (this instanceof SuggestionDto) {
			return TagType.SUGGESTION;
		} else {
			return null;
		}
	}
}
